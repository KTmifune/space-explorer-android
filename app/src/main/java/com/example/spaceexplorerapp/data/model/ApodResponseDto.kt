package com.example.spaceexplorerapp.data.model


import com.example.spaceexplorerapp.domain.model.ApodInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Astronomy Picture of the Day \

@Serializable
data class ApodResponseDto(
    @SerialName("copyright")
    val copyright: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("explanation")
    val explanation: String = "",
    @SerialName("hdurl")
    val hdurl: String = "",
    @SerialName("media_type")
    val mediaType: String = "",
    @SerialName("service_version")
    val serviceVersion: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("url")
    val url: String = ""
)


fun ApodResponseDto.toPhoto(): ApodInfo {
    return ApodInfo(
        title = title,
        copyright = copyright,
        date = date,
        description = explanation,
        mediaType = mediaType,
        serviceVersion = serviceVersion,
        imageUrl = url,
        highImageUrl = hdurl
    )
}