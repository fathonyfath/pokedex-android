package id.fathonyfath.pokedex.utils

/**
 * Created by fathonyfath on 06/02/18.
 */

fun getIdFromURI(uri: String): Int? {
    return Regex("\\/\\d+\\/\$").find(uri)?.value?.replace(Regex("\\/"), "")?.toInt()
}

fun removeDash(name: String): String {
    return name.replace(Regex("\\-"), " ")
}

fun capitalizeFirstLetter(name: String): String {
    return name[0].toUpperCase() + name.substring(1)
}