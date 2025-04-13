package com.example.spaceexplorerapp.di

import com.example.spaceexplorerapp.BuildConfig
import com.example.spaceexplorerapp.common.NASA_BASE_URL
import com.example.spaceexplorerapp.data.repository.NasaApiRepositoryImpl
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import com.example.spaceexplorerapp.network.RetrofitNasaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    // --- JSON (Kotlinx Serialization) ---
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    // --- OkHttpClient ---
    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        // デバッグビルドの際はログを出す
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        callFactory: Call.Factory,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(NASA_BASE_URL)
        .callFactory(callFactory)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()


    @Provides
    @Singleton
    fun provideNasaApi(retrofit: Retrofit): RetrofitNasaApi =
        retrofit.create(RetrofitNasaApi::class.java)


    // --- Repository ---
    @Provides
    @Singleton
    fun provideNasaApiRepository(api: RetrofitNasaApi): NasaApiRepository {
        return NasaApiRepositoryImpl(api)
    }

}