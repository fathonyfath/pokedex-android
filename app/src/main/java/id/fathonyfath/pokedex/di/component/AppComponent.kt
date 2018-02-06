package id.fathonyfath.pokedex.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import id.fathonyfath.pokedex.App
import id.fathonyfath.pokedex.di.ActivityBuilder
import id.fathonyfath.pokedex.di.module.AppModule
import javax.inject.Singleton

/**
 * Created by fathonyfath on 04/02/18.
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}