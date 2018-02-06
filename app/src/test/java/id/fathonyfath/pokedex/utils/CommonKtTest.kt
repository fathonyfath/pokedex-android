package id.fathonyfath.pokedex.utils

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by fathonyfath on 06/02/18.
 */
class CommonKtTest {

    @Test
    fun getIdFromURI_isCorrect() {
        assertEquals(1, getIdFromURI("https://pokeapi.co/api/v2/pokemon-species/1/"))
        assertEquals(null, getIdFromURI("https://pokeapi.co/api/v2/pokemon"))
    }

    @Test
    fun removeDash_isCorrect() {
        assertEquals("pokemon", removeDash("pokemon"))
        assertEquals("pokemon name", removeDash("pokemon-name"))
        assertEquals("some pokemon name", removeDash("some-pokemon-name"))
    }

    @Test
    fun capitalizeFirstLetter_isCorrect() {
        assertEquals("Bulbasaur", capitalizeFirstLetter("bulbasaur"))
        assertEquals("Some pokemon", capitalizeFirstLetter("some pokemon"))
    }

}