package com.example.spaceexplorerapp.data.detasource.remote

import com.example.spaceexplorerapp.data.model.ApodResponseDto
import com.example.spaceexplorerapp.data.network.RetrofitNasaApi
import javax.inject.Inject

class NasaRemoteDataSourceImpl @Inject constructor(
    private val api: RetrofitNasaApi
) : NasaRemoteDataSource {
    override suspend fun getApod(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean
    ): ApodResponseDto {
        return api.getApod(date, startDate, endDate, count, thumbs)
    }
}
