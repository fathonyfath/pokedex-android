package id.fathonyfath.pokedex.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.di.ViewModelBuilder
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

}