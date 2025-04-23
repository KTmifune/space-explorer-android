package com.example.spaceexplorerapp.domain.reposiroty

import com.example.spaceexplorerapp.domain.model.ApodPhoto


interface NasaApiRepository {

    suspend fun getAstronomyPicture(
        date: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        count: Int? = null,
        thumbs: Boolean = false
    ): ApodPhoto
}