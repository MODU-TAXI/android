package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.ImageToUrlResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {


    @Multipart
    @POST("/api/s3")
    suspend fun imageToUrl(
        @Part image: MultipartBody.Part
    ): ImageToUrlResponse
}