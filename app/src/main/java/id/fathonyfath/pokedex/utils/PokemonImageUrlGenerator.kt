package id.fathonyfath.pokedex.utils

/**
 * Created by fathonyfath on 06/02/18.
 */
class PokemonImageUrlGenerator(private val baseImageUrl: String) {

    fun getImageUrl(pokemonId: Int): String {
        return baseImageUrl + pokemonId.toString() + ".png"
    }

}