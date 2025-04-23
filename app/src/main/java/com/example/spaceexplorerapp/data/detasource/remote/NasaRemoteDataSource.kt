package com.example.spaceexplorerapp.data.detasource.remote

import com.example.spaceexplorerapp.data.model.ApodResponseDto

interface NasaRemoteDataSource {
    suspend fun getApod(
        date: String?,
        startDate: String?,
        endDate: String?,
        count: Int?,
        thumbs: Boolean
    ): ApodResponseDto
}