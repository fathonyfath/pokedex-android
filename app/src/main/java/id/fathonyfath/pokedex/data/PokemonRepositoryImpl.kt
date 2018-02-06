package id.fathonyfath.pokedex.data

import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.utils.PokemonImageUrlGenerator
import id.fathonyfath.pokedex.utils.capitalizeFirstLetter
import id.fathonyfath.pokedex.utils.getIdFromURI
import id.fathonyfath.pokedex.utils.removeDash
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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

    override fun getPokemonDetail(pokemonId: Int): Single<Pokemon> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}