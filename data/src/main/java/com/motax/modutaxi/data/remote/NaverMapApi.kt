package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.AddressFromGeoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapApi {

    @GET("/map-reversegeocode/v2/gc")
    suspend fun getAddressFromGeo(
        @Query("request") request: String,
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String,
    ): AddressFromGeoResponse
}