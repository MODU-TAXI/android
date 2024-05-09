package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @GET("/api/rooms/list")
    suspend fun getTaxiPotList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): TaxiPotListResponse

    @POST("/room-waiting/{roomId}/members")
    suspend fun enterPot(
        @Path("roomId") roomId: Long
    ): Unit


}