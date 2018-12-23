package id.fathonyfath.pokedex.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.util.Log
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import id.fathonyfath.pokedex.App
import id.fathonyfath.pokedex.di.component.DaggerAppComponent
import id.fathonyfath.pokedex.di.module.NetModule

/**
 * Created by fathonyfath on 04/02/18.
 */

object AppInjector {

    fun init(app: App) {
        DaggerAppComponent
                .builder()
                .application(app)
                .netModule(NetModule.instance)
                .build()
                .inject(app)

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                if(activity is Injectable) handleActivityInjection(activity)
            }

            override fun onActivityPaused(activity: Activity) = Unit
            override fun onActivityResumed(activity: Activity) = Unit
            override fun onActivityStarted(activity: Activity) = Unit
            override fun onActivityDestroyed(activity: Activity) = Unit
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) = Unit
            override fun onActivityStopped(activity: Activity) = Unit
        })
    }


    private fun handleActivityInjection(activity: Activity) {
        AndroidInjection.inject(activity)

        val fragmentActivity = activity as FragmentActivity
        fragmentActivity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentAttached(fm, f, context)
                if(f is Injectable) handleFragmentInjection(f)
            }

        }, true)
    }

    private fun handleFragmentInjection(fragment: Fragment) {
        AndroidSupportInjection.inject(fragment)
    }
}