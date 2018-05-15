package id.fathonyfath.pokedex.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
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
open class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providePokemonInMemoryStorage(): InMemoryStorage = InMemoryStorage()

    @Provides
    @Singleton
    fun providePokemonRepository(pokeAPI: PokeAPI,
                                 pokemonInMemoryStorage: InMemoryStorage,
                                 pokemonImageGenerator: PokemonImageGenerator): PokemonRepository =
            PokemonRepositoryImpl(pokeAPI, pokemonInMemoryStorage, pokemonImageGenerator)

}