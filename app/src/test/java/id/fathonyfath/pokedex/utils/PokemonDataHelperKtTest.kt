package id.fathonyfath.pokedex.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonDataHelperKtTest {

    @Test
    fun getIdFromURI_isCorrect() {
        assertEquals(1, "https://pokeapi.co/api/v2/pokemon-species/1/".getIdFromURI())
        assertEquals(-1, "https://pokeapi.co/api/v2/pokemon".getIdFromURI())
    }

    @Test
    fun removeDash_isCorrect() {
        assertEquals("pokemon", "pokemon".removeDash())
        assertEquals("pokemon name", "pokemon-name".removeDash())
        assertEquals("some pokemon name", "some-pokemon-name".removeDash())
    }

    @Test
    fun capitaizeFirstLetter_isCorrect() {
        assertEquals("Bulbasaur", "bulbasaur".capitalizeFirstLetter())
        assertEquals("Some pokemon", "some pokemon".capitalizeFirstLetter())
    }
}