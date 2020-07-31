package id.fathonyfath.pokedex

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import id.fathonyfath.pokedex.di.AppInjector
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = activityDispatchingAndroidInjector
}
