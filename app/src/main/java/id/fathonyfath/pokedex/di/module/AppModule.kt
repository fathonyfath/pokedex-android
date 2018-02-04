package id.fathonyfath.pokedex.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.di.ViewModelBuilder
import javax.inject.Singleton

/**
 * Created by fathonyfath on 04/02/18.
 */
@Module(includes = [
    ViewModelBuilder::class
])
open class AppModule {

    @Provides @Singleton
    fun provideContext(application: Application): Context = application

}