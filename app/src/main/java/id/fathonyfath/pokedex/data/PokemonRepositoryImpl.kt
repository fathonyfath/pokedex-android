package id.fathonyfath.pokedex.data

import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.model.Profile
import id.fathonyfath.pokedex.utils.PokemonImageUrlGenerator
import id.fathonyfath.pokedex.utils.capitalizeFirstLetter
import id.fathonyfath.pokedex.utils.getIdFromURI
import id.fathonyfath.pokedex.utils.removeDash
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by fathonyfath on 04/02/18.
 */

class PokemonRepositoryImpl(
        private val pokeAPI: PokeAPI,
        private val pokemonImageUrlGenerator: PokemonImageUrlGenerator)
    : PokemonRepository {

    override fun getPokemonList(offset: Int): Single<List<Pokemon>> {
        return pokeAPI.getPokemonList(offset)
                .map {
                    val pokemonList = arrayListOf<Pokemon>()
                    for (pokemon in it.results) {
                        val pokemonId = getIdFromURI(pokemon.url)
                        pokemonId?.let {
                            pokemonList.add(Pokemon(it,
                                    capitalizeFirstLetter(removeDash(pokemon.name)),
                                    pokemonImageUrlGenerator.getImageUrl(it)))
                        }
                    }
                    return@map pokemonList.toList()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun getPokemonDetail(pokemonId: Int): Single<Pair<Int, Detail>> {
        return pokeAPI.getPokemonDetail(pokemonId)
                .map {
                    val abilities = it.abilities.map { it.abilityDetail.name }.map { capitalizeFirstLetter(removeDash(it)) }
                    val types = it.types.map { it.typeDetail.name }.map { capitalizeFirstLetter(removeDash(it)) }

                    val stats = mutableMapOf<String, Int>()

                    it.stats.forEach {
                        stats[capitalizeFirstLetter(removeDash(it.statDetail.name))] = it.baseStat
                    }

                    val detail = Detail(types, abilities,
                            Profile(it.weight, it.height, it.baseExperience), stats)

                    return@map Pair(pokemonId, detail)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }
}