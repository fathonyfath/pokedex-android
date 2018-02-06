package id.fathonyfath.pokedex.data.repository

import id.fathonyfath.pokedex.model.Pokemon
import io.reactivex.Single

/**
 * Created by fathonyfath on 04/02/18.
 */
interface PokemonRepository {

    fun getPokemonList(offset: Int): Single<List<Pokemon>>
    fun getPokemonDetail(pokemonId: Int): Single<Pokemon>

}