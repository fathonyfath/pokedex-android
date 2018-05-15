package id.fathonyfath.pokedex.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PokemonImageGeneratorTest {

    private val dummyImageUrl = "http://www.dummyserver.com/"

    lateinit var pokemonImageGenerator: PokemonImageGenerator

    @Before
    fun setUp() {
        pokemonImageGenerator = PokemonImageGenerator(dummyImageUrl)
    }

    @Test
    fun getImageUrl_isCorrect() {
        val dummyId = 4
        assertEquals("$dummyImageUrl$dummyId.png", pokemonImageGenerator.getImageUrl(dummyId))
    }
}