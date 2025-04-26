package com.example.spaceexplorerapp.data.network

import com.example.spaceexplorerapp.data.model.ApodResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitNasaApi {

    @GET("planetary/apod")
    suspend fun gatApod(
        @Query("date") date: String? = null,                     // 任意: 1日分
        @Query("start_date") startDate: String? = null,          // 任意: 期間指定（開始）
        @Query("end_date") endDate: String? = null,              // 任意: 期間指定（終了）
        @Query("count") count: Int? = null,                      // 任意: ランダム取得（件数）
        @Query("thumbs") thumbs: Boolean = false                 // 任意: 動画サムネ取得
    ): ApodResponseDto

//    @Headers("Authorization: Client-ID ${Constants.API_KEY}")
//    @GET("search/photos")
//    suspend fun searchPhotos(@Query("query") query: String): SearchPhotosResultDto
//
//    @Headers("Authorization: Client-ID ${Constants.API_KEY}")
//    @GET("photos/{id}")
//    suspend fun getPhotoById(@Path("id") photoId: String): PhotoDetailDto


}