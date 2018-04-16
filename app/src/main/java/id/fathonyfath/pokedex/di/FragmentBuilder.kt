package id.fathonyfath.pokedex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.fathonyfath.pokedex.DetailDialog
import id.fathonyfath.pokedex.di.module.DetailDialogModule
import id.fathonyfath.pokedex.di.scopes.FragmentScope

/**
 * Created by fathonyfath on 21/03/18.
 */

@Module
interface FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailDialogModule::class])
    fun bindDetailDialog(): DetailDialog

}