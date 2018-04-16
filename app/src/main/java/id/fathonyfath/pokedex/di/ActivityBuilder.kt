package id.fathonyfath.pokedex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.fathonyfath.pokedex.MainActivity
import id.fathonyfath.pokedex.di.module.MainActivityModule
import id.fathonyfath.pokedex.di.scopes.ActivityScope

/**
 * Created by fathonyfath on 04/02/18.
 */

@Module
interface ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentBuilder::class])
    fun bindMainActivity(): MainActivity

}