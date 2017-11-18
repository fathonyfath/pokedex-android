package id.fathonyfath.pokedex.model

/**
 * Created by fathonyfath on 17/11/17.
 */

data class Pokemon(val id: Int, val name: String, val imageUrl: String) {

    var types: List<String>? = null
    var abilities: List<String>? = null
    var profile: Profile? = null
    var stat: Stat? = null

}

data class Profile(val weight: Int, val height: Int, val baseExperience: Int)

data class Stat(val hp: Int, val speed: Int, val attack: Int, val defense: Int,
                val specialAttack: Int, val specialDefense: Int)