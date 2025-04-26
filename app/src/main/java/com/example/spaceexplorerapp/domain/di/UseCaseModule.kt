package com.example.spaceexplorerapp.domain.di

import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import com.example.spaceexplorerapp.domain.usecase.GetAstronomyPictureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * UseCase の DI モジュール
 *
 * 各ユースケースを Hilt で注入できるように提供します。
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAstronomyPictureUseCase(
        repository: NasaApiRepository
    ): GetAstronomyPictureUseCase =
        GetAstronomyPictureUseCase(repository)
}
