package id.fathonyfath.pokedex.data.api

import id.fathonyfath.pokedex.data.api.response.PokemonList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by fathonyfath on 02/02/18.
 */
interface PokeAPI {

    @GET("api/v2/pokemon-species")
    fun getPokemonList(@Query("offset") offset: Int): Single<PokemonList>

    @GET("api/v2/pokemon/{pokemonId}")
    fun getPokemonDetail(@Path("pokemonId") pokemonId: Int): Single<Any>

}