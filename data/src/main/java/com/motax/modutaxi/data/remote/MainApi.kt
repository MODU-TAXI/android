package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET("/api/rooms/list")
    suspend fun getTaxiPotList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): TaxiPotListResponse
}