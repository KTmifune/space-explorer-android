package com.example.spaceexplorerapp.data.repository

import com.example.spaceexplorerapp.data.detasource.remote.NasaRemoteDataSource
import com.example.spaceexplorerapp.data.model.toPhoto
import com.example.spaceexplorerapp.domain.model.ApodPhoto
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import javax.inject.Inject

class NasaApiRepositoryImpl @Inject constructor(
    private val remoteDatasource: NasaRemoteDataSource
) : NasaApiRepository {
    override suspend fun getAstronomyPicture(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean
    ): ApodPhoto {
        // TODO:データが取得できななかった場合ローカルから取得する
        return try {
            remoteDatasource.getApod(date, startDate, endDate, count, thumbs).toPhoto()
        } catch (e: Exception) {
            ApodPhoto()
        }
    }
}