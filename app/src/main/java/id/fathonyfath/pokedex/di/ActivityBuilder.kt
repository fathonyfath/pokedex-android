package id.fathonyfath.pokedex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.fathonyfath.pokedex.MainActivity
import id.fathonyfath.pokedex.di.module.MainActivityModule

/**
 * Created by fathonyfath on 04/02/18.
 */
@Module
interface ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun bindMainActivity(): MainActivity

}