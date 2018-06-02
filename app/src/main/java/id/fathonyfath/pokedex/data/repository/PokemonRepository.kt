package id.fathonyfath.pokedex.data.repository

import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.utils.Either
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by fathonyfath on 04/02/18.
 */
interface PokemonRepository {

    fun listenToPokemonList(): Observable<List<Pokemon>>
    fun listenToPokemonId(pokemonId: Int): Observable<Pokemon>
    fun fetchPokemonDetail(pokemonId: Int): Single<Either<Throwable, Boolean>>
    fun fetchMorePokemon(offsetPokemonId: Int): Single<Either<Throwable, Boolean>>

}