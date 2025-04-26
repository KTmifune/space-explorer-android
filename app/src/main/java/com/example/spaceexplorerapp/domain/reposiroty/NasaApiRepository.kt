package com.example.spaceexplorerapp.domain.reposiroty

import com.example.spaceexplorerapp.domain.model.ApodInfo


interface NasaApiRepository {

    suspend fun getApodInfo(
        date: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        count: Int? = null,
        thumbs: Boolean = false
    ): ApodInfo?
}