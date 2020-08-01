package id.fathonyfath.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by fathonyfath on 04/02/18.
 */

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
