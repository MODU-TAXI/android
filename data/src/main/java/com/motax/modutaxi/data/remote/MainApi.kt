package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.request.CreateTaxiPotRequest
import com.motax.modutaxi.data.model.response.GetNearSpotResponse
import com.motax.modutaxi.data.model.response.GetSpotListResponse
import com.motax.modutaxi.data.model.response.MemberDetailResponse
import com.motax.modutaxi.data.model.response.TaxiPotDetailResponse
import com.motax.modutaxi.data.model.response.TaxiPotListRadiusResponse
import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import com.motax.modutaxi.data.model.response.TaxiPotParticipants
import com.motax.modutaxi.data.model.response.TaxiPotPreviewResponse
import com.motax.modutaxi.data.model.response.TaxiPotWaitingMembers
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MainApi {

    @GET("/api/rooms/map")
    suspend fun getTaxiPotListRadius(
        @QueryMap filter: Map<String, Long>,
        @Query("radius") radius: Int,
        @Query("searchLatitude") latitude: Double,
        @Query("searchLongitude") longitude: Double,
        @Query("roomTags") roomTags: List<String>
    ): TaxiPotListRadiusResponse

    @GET("/api/rooms/list")
    suspend fun getTaxiPotList(
        @QueryMap filter: Map<String, Long>,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("radius") radius: Int,
        @Query("searchLatitude") latitude: Double,
        @Query("searchLongitude") longitude: Double,
        @Query("sortType") sortType: String,
        @Query("roomTags") roomTags: List<String>
    ): TaxiPotListResponse

    @GET("/api/rooms/integration")
    suspend fun getTaxiPotListIntegration(
        @QueryMap filter: Map<String, Long>,
        @Query("radius") radius: Int,
        @Query("searchLatitude") latitude: Double,
        @Query("searchLongitude") longitude: Double,
        @Query("sortType") sortType: String,
        @Query("roomTags") roomTags: List<String>,
        @Query("isImminent") isImminent: Boolean
    ): TaxiPotListResponse

    @GET("/api/rooms/preview/{id}")
    suspend fun getTaxiPotPreview(
        @Path("id") id: Long
    ): TaxiPotPreviewResponse

    @GET("/api/spots/map")
    suspend fun getNearSpot(
        @Query("count") count: Int,
        @Query("searchLatitude") latitude: Double,
        @Query("searchLongitude") longitude: Double
    ): GetNearSpotResponse

    @GET("/api/spots/list")
    suspend fun getSpotList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("searchLongitude") searchLongitude: Double,
        @Query("searchLatitude") searchLatitude: Double
    ): GetSpotListResponse

    @POST("/api/rooms")
    suspend fun createTaxiPot(
        @Body params: CreateTaxiPotRequest
    ): TaxiPotDetailResponse

    @GET("/api/rooms/{roomId}")
    suspend fun getTaxiPotDetail(
        @Path("roomId") roomId: Long
    ): TaxiPotDetailResponse

    @GET("/api/rooms/{roomId}/members/in")
    suspend fun getTaxiPotParticipants(
        @Path("roomId") roomId: Long
    ): TaxiPotParticipants

    @GET("/api/rooms/{roomId}/members/waiting")
    suspend fun getTaxiPotWaitingMembers(
        @Path("roomId") roomId: Long
    ): TaxiPotWaitingMembers

    @POST("/api/rooms/{roomId}/apply")
    suspend fun enterTaxiPot(
        @Path("roomId") roomId: Long
    ): Unit

    @DELETE("/api/rooms/{roomId}/members/{memberId}/approve")
    suspend fun approveEnterTaxiPot(
        @Path("roomId") roomId: Long,
        @Path("memberId") memberId: Long
    ): Unit

    @GET("/api/members/{memberId}")
    suspend fun getMemberDetail(
        @Path("memberId") memberId: Long
    ):MemberDetailResponse
}