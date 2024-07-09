package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.ChatMessageData
import com.motax.modutaxi.domain.model.ChatInfoData
import com.motax.modutaxi.domain.model.MemberProfileData
import com.motax.modutaxi.domain.model.MemberDetailData
import com.motax.modutaxi.domain.model.MonthlyUsageHistoryData
import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusData
import com.motax.modutaxi.domain.model.TaxiPotParticipantsData
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.model.TaxiPotWaitingMembersData
import java.net.URI
import com.motax.modutaxi.domain.model.UpdateMemberData
import com.motax.modutaxi.domain.model.UploadImageData
import com.motax.modutaxi.domain.model.UsageDetailData
import java.io.File

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

    suspend fun getChatMessages(
        roomId: Long
    ): Result<ChatMessageData>


    suspend fun getChatsInfo(): Result<ChatInfoData>

    suspend fun getMemberProfile(
        memberId: Long
    ): Result<MemberProfileData>

    suspend fun getMemberDetail(
        memberId: Long
    ): Result<MemberDetailData>

    suspend fun updateMemberProfile(
        profileData: Map<String, String>
    ): Result<UpdateMemberData>

    suspend fun uploadFile(
        file: File
    ): Result<UploadImageData>

    suspend fun getMonthlyUsageHistory(
        year: Int, month: Int
    ): Result<MonthlyUsageHistoryData>

    suspend fun getUsageDetail(
        id: Long
    ): Result<UsageDetailData>

    suspend fun deleteMember(): Result<Unit>

    suspend fun logout(): Result<Unit>
}