package id.fathonyfath.pokedex.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.PokemonRepositoryImpl
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import javax.inject.Singleton

/**
 * Created by fathonyfath on 04/02/18.
 */

@Module
open class MainActivityModule {

    @Provides
    fun providePokemonRepository(context: Context) : PokemonRepository =
            PokemonRepositoryImpl(context)

}