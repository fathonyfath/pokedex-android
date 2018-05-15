package id.fathonyfath.pokedex.data

import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.data.storage.InMemoryStorage
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.model.Profile
import id.fathonyfath.pokedex.utils.PokemonImageGenerator
import id.fathonyfath.pokedex.utils.capitalizeFirstLetter
import id.fathonyfath.pokedex.utils.getIdFromURI
import id.fathonyfath.pokedex.utils.removeDash
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by fathonyfath on 04/02/18.
 */

class PokemonRepositoryImpl(
        private val pokeAPI: PokeAPI,
        private val pokemonStorage: InMemoryStorage,
        private val pokemonImageGenerator: PokemonImageGenerator)
    : PokemonRepository {

    private val pokemonDetailRequestQueue: MutableSet<Int> = mutableSetOf()

    override fun listenToPokemonList(): Observable<List<Pokemon>> {
        return pokemonStorage
                .listenToPokemonMap()
                .map { it.map { it.value } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun listenToPokemonId(pokemonId: Int): Observable<Pokemon> {
        return pokemonStorage
                .listenToPokemonId(pokemonId)
                .map { it.second }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun fetchPokemonDetail(pokemonId: Int): Completable {
        if (pokemonStorage.getPokemon(pokemonId)?.detail == null &&
                !pokemonDetailRequestQueue.contains(pokemonId)) {
            pokemonDetailRequestQueue.add(pokemonId)
            return pokeAPI.getPokemonDetail(pokemonId)
                    .map {
                        val abilities = it.abilities.map { it.abilityDetail.name }.map {
                            it.removeDash().capitalizeFirstLetter()
                        }
                        val types = it.types.map { it.typeDetail.name }.map {
                            it.removeDash().capitalizeFirstLetter()
                        }

                        val stats = mutableMapOf<String, Int>()

                        it.stats.forEach {
                            stats[it.statDetail.name.removeDash().capitalizeFirstLetter()] = it.baseStat
                        }

                        val detail = Detail(types, abilities,
                                Profile(it.weight, it.height, it.baseExperience), stats)

                        val pokemon = Pokemon(
                                it.id,
                                it.name.removeDash().capitalizeFirstLetter(),
                                pokemonImageGenerator.getImageUrl(it.id), detail)

                        return@map Pair(pokemonId, pokemon)
                    }
                    .doOnSuccess { pokemonStorage.putPokemon(it.first, it.second) }
                    .doOnSuccess { pokemonDetailRequestQueue.remove(pokemonId) }
                    .doOnError { pokemonDetailRequestQueue.remove(pokemonId) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .toCompletable()
        } else {
            return Completable.complete()
        }
    }

    override fun fetchMorePokemon(offsetPokemonId: Int): Single<Boolean> {
        return pokeAPI.getPokemonList(offsetPokemonId)
                .map {
                    val pokemonList = arrayListOf<Pokemon>()
                    for (pokemon in it.results) {
                        val pokemonId = pokemon.url.getIdFromURI()
                        pokemonId.let {
                            pokemonList.add(
                                    Pokemon(
                                            it,
                                            pokemon.name.removeDash().capitalizeFirstLetter(),
                                            pokemonImageGenerator.getImageUrl(it)
                                    )
                            )
                        }
                    }
                    return@map pokemonList.toList()
                }
                .doOnSuccess { it.forEach { item -> pokemonStorage.putPokemon(item.id, item) } }
                .map { it.isNotEmpty() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

}