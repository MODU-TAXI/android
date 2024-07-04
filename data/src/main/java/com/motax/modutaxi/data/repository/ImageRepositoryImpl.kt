package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.response.ImageToUrlResponse
import com.motax.modutaxi.data.remote.ImageApi
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: ImageApi
) : ImageRepository{

    override suspend fun imageToUrl(body: MultipartBody.Part): Result<ImageToUrlResponse> = runCatching {
        api.imageToUrl(body)
    }

}