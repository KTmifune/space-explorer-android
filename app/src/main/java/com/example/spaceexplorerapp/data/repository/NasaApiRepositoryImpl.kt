package com.example.spaceexplorerapp.data.repository

import com.example.spaceexplorerapp.data.model.ApodResponseDto
import com.example.spaceexplorerapp.domain.reposiroty.NasaApiRepository
import com.example.spaceexplorerapp.network.RetrofitNasaApi
import javax.inject.Inject

class NasaApiRepositoryImpl @Inject constructor(
    private val api: RetrofitNasaApi
) : NasaApiRepository {
    override suspend fun getAstronomyPicture(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean
    ): ApodResponseDto {
        return api.gatAstronomyPicture(date, startDate, endDate, count, thumbs)
    }

}