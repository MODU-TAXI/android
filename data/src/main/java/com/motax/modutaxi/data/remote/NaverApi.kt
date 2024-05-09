package com.motax.modutaxi.data.remote

import retrofit2.http.GET

interface NaverApi {

    @GET("/v1/search/local.json")
    suspend fun getAddressInfo(

    )
}