package id.fathonyfath.pokedex.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.data.storage.InMemoryStorage
import id.fathonyfath.pokedex.di.ViewModelBuilder
import id.fathonyfath.pokedex.utils.PokemonImageGenerator
import javax.inject.Singleton

/**
 * Created by fathonyfath on 04/02/18.
 */
@Module(includes = [
    ViewModelBuilder::class
])
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providePokemonRepository(pokeAPI: PokeAPI,
                                 pokemonStorage: InMemoryStorage,
                                 pokemonImageGenerator: PokemonImageGenerator): PokemonRepository =
            PokemonRepositoryImpl(pokeAPI, pokemonStorage, pokemonImageGenerator)

}