package id.fathonyfath.pokedex.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.fathonyfath.pokedex.BuildConfig
import id.fathonyfath.pokedex.data.api.PokeAPI
import id.fathonyfath.pokedex.utils.PokemonImageGenerator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by fathonyfath on 06/02/18.
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetModule {

    @Provides
    @Singleton
    @Named("SERVER_URL")
    fun provideBaseUrl(): String = BuildConfig.SERVER_URL

    @Provides
    @Singleton
    @Named("IMAGE_URL")
    fun provideImageUrl(): String = BuildConfig.IMAGE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
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
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(@Named("SERVER_URL") baseUrl: String,
                        okHttpClient: OkHttpClient,
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
    fun providePokemonImageGenerator(@Named("IMAGE_URL") baseImageUrl: String): PokemonImageGenerator {
        return PokemonImageGenerator(baseImageUrl)
    }
}