package id.fathonyfath.pokedex.di.module

import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.utils.PokemonImageUrlGenerator

/**
 * Created by fathonyfath on 21/03/18.
 */
@Module
class DetailDialogModule {

    @Provides
    fun providePokemonRepository(pokeAPI: PokeAPI, pokemonImageUrlGenerator: PokemonImageUrlGenerator): PokemonRepository =
            PokemonRepositoryImpl(pokeAPI, pokemonImageUrlGenerator)

}