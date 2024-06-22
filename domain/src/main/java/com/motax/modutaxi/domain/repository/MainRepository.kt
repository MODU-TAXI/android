package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.MemberDetailData
import com.motax.modutaxi.domain.model.MemberInfoData
import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusData
import com.motax.modutaxi.domain.model.TaxiPotParticipantsData
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.model.TaxiPotWaitingMembersData

interface MainRepository {

    suspend fun getTaxiPotListRadius(
        filter: Map<String, Long>,
        radius: Int,
        latitude: Double,
        longitude: Double,
        roomTags: List<String>
    ): Result<TaxiPotListRadiusData>

    suspend fun getTaxiPotList(
        filter: Map<String, Long>,
        page: Int,
        size: Int,
        radius: Int,
        latitude: Double,
        longitude: Double,
        sortType: String,
        roomTags: List<String>
    ): Result<TaxiPotListData>

    suspend fun getTaxiPotListIntegration(
        filter: Map<String, Long>,
        radius: Int,
        latitude: Double,
        longitude: Double,
        sortType: String,
        roomTags: List<String>,
        isImminent : Boolean
    ): Result<TaxiPotListData>

    suspend fun getTaxiPotPreview(
        id: Long
    ): Result<TaxiPotPreviewData>

    suspend fun getNearSpot(
        count: Int,
        latitude: Double,
        longitude: Double
    ): Result<NearSpotData>

    suspend fun getSpotList(
        page: Int,
        size: Int,
        searchLongitude: Double,
        searchLatitude: Double
    ): Result<SpotListData>

    suspend fun createTaxiPot(
        spotId: Long,
        roomTagBitMask: List<String>,
        departureLongitude: Double,
        departureLatitude: Double,
        departureTime: String,
        departureName: String,
        wishHeadcount: Int
    ): Result<TaxiPotDetailData>

    suspend fun getTaxiPotDetail(
        roomId: Long
    ): Result<TaxiPotDetailData>

    suspend fun getTaxiPotParticipants(
        roomId: Long
    ): Result<TaxiPotParticipantsData>

    suspend fun getTaxiPotWaitingMembers(
        roomId: Long
    ): Result<TaxiPotWaitingMembersData>

    suspend fun enterTaxiPot(
        roomId: Long
    ): Result<Unit>

    suspend fun approveEnterTaxiPot(
        roomId: Long,
        memberId: Long
    ): Result<Unit>

    suspend fun getMemberDetail(
        memberId: Long
    ): Result<MemberDetailData>
}