package id.fathonyfath.pokedex.utils

/**
 * Created by fathonyfath on 06/02/18.
 */
class PokemonDataHelper(private val baseImageUrl: String) {

    fun getImageUrl(pokemonId: Int): String {
        return baseImageUrl + pokemonId.toString() + ".png"
    }

    fun getIdFromURI(uri: String): Int? {
        return Regex("\\/\\d+\\/\$").find(uri)?.value?.replace(Regex("\\/"), "")?.toInt()
    }

    fun removeDash(name: String): String {
        return name.replace(Regex("\\-"), " ")
    }

    fun capitalizeFirstLetter(name: String): String {
        return name[0].toUpperCase() + name.substring(1)
    }

}