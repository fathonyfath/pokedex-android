package id.fathonyfath.pokedex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.fathonyfath.pokedex.MainActivity

/**
 * Created by fathonyfath on 04/02/18.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    abstract fun bindMainActivity(): MainActivity
}