package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.response.ImageToUrlResponse
import okhttp3.MultipartBody

interface ImageRepository {

    suspend fun imageToUrl(
        body: MultipartBody.Part
    ): Result<ImageToUrlResponse>
}