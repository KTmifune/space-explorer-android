package com.example.spaceexplorerapp.data.repository

import com.example.spaceexplorerapp.data.detasource.remote.NasaRemoteDataSource
import com.example.spaceexplorerapp.data.model.toPhoto
import com.example.spaceexplorerapp.domain.model.ApodInfo
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import javax.inject.Inject

class NasaApiRepositoryImpl @Inject constructor(
    private val remoteDatasource: NasaRemoteDataSource
) : NasaApiRepository {
    override suspend fun getApodInfo(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean
    ): ApodInfo? {
        // TODO:データが取得できななかった場合ローカルから取得する
        try {
            val data = remoteDatasource.getApod(date, startDate, endDate, count, thumbs)
            return data.toPhoto()
        } catch (e: Exception) {
            println("${e.message}")
            return null
        }
    }
}