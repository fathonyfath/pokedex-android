package id.fathonyfath.pokedex.data.repository

import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by fathonyfath on 04/02/18.
 */
interface PokemonRepository {

    fun listenToPokemonList(): Observable<List<Pokemon>>
    fun listenToPokemonId(pokemonId: Int): Observable<Pokemon>
    fun fetchPokemonDetail(pokemonId: Int): Completable
    fun fetchMorePokemon(offsetPokemonId: Int): Single<Boolean>

}