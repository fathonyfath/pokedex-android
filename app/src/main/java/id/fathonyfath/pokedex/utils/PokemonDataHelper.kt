package id.fathonyfath.pokedex.utils

/**
 * Created by fathonyfath on 06/02/18.
 */
class PokemonImageGenerator(private val baseImageUrl: String) {
    fun getImageUrl(pokemonId: Int): String = "$baseImageUrl$pokemonId.png"
}

fun String.getIdFromURI(): Int {
    return Regex("/\\d+/\$").find(this)?.value?.replace(Regex("/"), "")?.toInt() ?: -1
}

fun String.removeDash(): String = this.replace(Regex("-"), " ")

fun String.capitalizeFirstLetter(): String = "${this[0].toUpperCase()}${this.substring(1)}"