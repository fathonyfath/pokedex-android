package id.fathonyfath.pokedex.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.fathonyfath.pokedex.MainViewModel

/**
 * Created by fathonyfath on 04/02/18.
 */

@Module
interface ViewModelBuilder {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindPokemonRepository(mainViewModel: MainViewModel): ViewModel

}