package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.GetSpotListResponse
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

    @GET("/api/spots/map")
    suspend fun getSpotRadius(
        @Query("radius") radius: Long,
        @Query("searchLatitude") latitude: Double,
        @Query("searchLongitude") longitude: Double
    ): GetSpotListResponse

    @GET("/api/spots/list")
    suspend fun getSpotList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("searchLongitude") searchLongitude: Double,
        @Query("searchLatitude") searchLatitude: Double
    ): GetSpotListResponse


}