package id.fathonyfath.pokedex.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.utils.PokemonDataHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by fathonyfath on 06/02/18.
 */
@Module
open class NetModule(private val baseUrl: String, private val baseImageUrl: String) {

    companion object {
        val instance = NetModule("http://pokeapi.salestock.net/",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/")
    }

    @Provides
    @Singleton
    fun provideLoggingInterecptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideRx2CallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideGsonCoverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        rx2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rx2CallAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun providePokeAPI(retrofit: Retrofit): PokeAPI {
        return retrofit.create(PokeAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonImageUrlGenerator(): PokemonDataHelper {
        return PokemonDataHelper(baseImageUrl)
    }
}