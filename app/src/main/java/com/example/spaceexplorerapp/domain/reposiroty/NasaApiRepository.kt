package com.example.spaceexplorerapp.domain.reposiroty

import com.example.spaceexplorerapp.data.model.ApodResponseDto


interface NasaApiRepository {

    suspend fun getAstronomyPicture(
        date: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        count: Int? = null,
        thumbs: Boolean = false
    ): ApodResponseDto
}