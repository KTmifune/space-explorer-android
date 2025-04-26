package com.example.spaceexplorerapp.data.network.di

import com.example.spaceexplorerapp.BuildConfig
import com.example.spaceexplorerapp.common.NASA_BASE_URL
import com.example.spaceexplorerapp.data.network.RetrofitNasaApi
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

/**
 *  通信レイヤ専用の Hilt モジュール。
 *
 *   • Json / OkHttpClient / Retrofit いずれも Builder パターンで
 *   複雑な初期化を行うため、@Binds ではなく @Provides が必要
 *   • 生成時に BuildConfig.DEBUG を参照するなどランタイム条件分岐を挟むため手動 new が必要。
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    // --------------------------------------------------------
    // 1) JSON シリアライザ
    // --------------------------------------------------------
    /**
     * Kotlinx Serialization 用の Json インスタンスを提供。
     * - ignoreUnknownKeys = true で不明プロパティを無視し、
     *   バックエンドがフィールドを増やしてもクラッシュしない
     *   レジリエンスを確保。
     */
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    // --------------------------------------------------------
    // 2) OkHttpClient (Call.Factory)
    // --------------------------------------------------------
    /**
     * Retrofit から利用する HTTP クライアント。
     *
     * HttpLoggingInterceptor は DEBUG ビルド時のみ BODY ログ
     * を出力して、リリースビルドではオーバーヘッドを排除。
     *
     * 戻り値を `Call.Factory` として公開することで、
     * 他の HTTP クライアント実装 (e.g. MockWebServer) に差し替えやすい形にしている。
     */
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

    // --------------------------------------------------------
    // 3) Retrofit
    // --------------------------------------------------------
    /**
     * BaseUrl, CallFactory, ConverterFactory を組み合わせてRetrofit を一度だけ生成。
     *
     * • baseUrl は NASA API 固定。
     * • Kotlinx Serialization コンバータで JSON ↔ DTO 変換。
     */
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

    // --------------------------------------------------------
    // 4) Retrofit インターフェース
    // --------------------------------------------------------
    /**
     * Retrofit の動的 Proxy で生成した API インターフェース。
     * DataSource / Repository が依存する公開窓口はここだけ。
     */
    @Provides
    @Singleton
    fun provideNasaApi(retrofit: Retrofit): RetrofitNasaApi =
        retrofit.create(RetrofitNasaApi::class.java)

}