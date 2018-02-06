package id.fathonyfath.pokedex.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by fathonyfath on 06/02/18.
 */
data class PokemonList(
        @SerializedName("count") var count: Int,
        @SerializedName("results") var results: List<PokemonListResult>
)

data class PokemonListResult(
        @SerializedName("url") val url: String,
        @SerializedName("name") val name: String
)