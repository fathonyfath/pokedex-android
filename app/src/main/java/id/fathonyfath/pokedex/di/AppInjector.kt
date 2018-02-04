package id.fathonyfath.pokedex.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dagger.android.AndroidInjection
import id.fathonyfath.pokedex.App
import id.fathonyfath.pokedex.di.component.DaggerAppComponent

/**
 * Created by fathonyfath on 04/02/18.
 */
fun App.initDaggerComponent() {
    DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

    registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            handleActivityInjection(activity)
        }

        override fun onActivityPaused(activity: Activity) = Unit
        override fun onActivityResumed(activity: Activity) = Unit
        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityDestroyed(activity: Activity) = Unit
        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
    })
}

fun handleActivityInjection(activity: Activity) {
    AndroidInjection.inject(activity)
}