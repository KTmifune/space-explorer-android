package com.example.spaceexplorerapp.di

import com.example.spaceexplorerapp.data.detasource.remote.NasaRemoteDataSource
import com.example.spaceexplorerapp.data.detasource.remote.NasaRemoteDataSourceImpl
import com.example.spaceexplorerapp.data.repository.NasaApiRepositoryImpl
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * データレイヤ専用の Hilt モジュール。
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    // --------------------------------------------------------
    // RemoteDataSource
    // --------------------------------------------------------
    /**
     *  ・NasaRemoteDataSource の実装クラスを公開
     *  ・実際に注入される先は NasaApiRepositoryImpl が主。
     *
     *  ★ なぜ @Binds？
     *    - ただ実装をインターフェースとして公開するだけ
     *      （Builder などの前処理が不要）
     *    - Dagger がキャストを生成するだけなので生成コードが最小
     *
     *  ★ スコープは @Singleton
     *    - Repository から複数回呼ばれても同じインスタンスを共有
     *
     *  ※ UI 層や他モジュールで直接使う想定はなく、Data 層内部の依存として閉じ込めておく設計方針。
     */
    @Binds
    @Singleton
    abstract fun bindNasaRemoteDataSource(impl: NasaRemoteDataSourceImpl): NasaRemoteDataSource

    // --------------------------------------------------------
    // Repository
    // --------------------------------------------------------
    /**
     * NasaApiRepositoryの実装クラスを公開する@Binds
     */
    @Binds
    @Singleton
    abstract fun bindNasaApiRepository(impl: NasaApiRepositoryImpl): NasaApiRepository

}