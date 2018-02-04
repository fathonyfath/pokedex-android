package id.fathonyfath.pokedex.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by fathonyfath on 02/02/18.
 */
interface PokeAPI {

    @GET("api/v2/pokemon-species")
    fun getPokemonList(@Query("offset") offset : Int) : Call<Any>

}