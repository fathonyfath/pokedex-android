package id.fathonyfath.pokedex.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.di.ViewModelBuilder
import id.fathonyfath.pokedex.utils.PokemonDataHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun providePokemonRepository(pokeAPI: PokeAPI, pokemonDataHelper: PokemonDataHelper): PokemonRepository =
            PokemonRepositoryImpl(pokeAPI, pokemonDataHelper)

}