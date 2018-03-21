package id.fathonyfath.pokedex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.fathonyfath.pokedex.DetailDialog
import id.fathonyfath.pokedex.di.module.DetailDialogModule

/**
 * Created by fathonyfath on 21/03/18.
 */

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector(modules = [DetailDialogModule::class])
    fun bindDetailDialog(): DetailDialog

}