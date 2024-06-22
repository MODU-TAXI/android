package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.model.request.CreateTaxiPotRequest
import com.motax.modutaxi.data.remote.MainApi
import com.motax.modutaxi.domain.model.MemberDetailData
import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.model.TaxiPotParticipantsData
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.model.TaxiPotWaitingMembersData
import com.motax.modutaxi.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {

    override suspend fun getTaxiPotListRadius(
        filter: Map<String, Long>,
        radius: Int,
        latitude: Double,
        longitude: Double,
        roomTags: List<String>
    ) = runCatching {
        api.getTaxiPotListRadius(filter, radius, latitude, longitude, roomTags)
    }.mapCatching { it.toDomain() }

    override suspend fun getTaxiPotList(
        filter: Map<String, Long>,
        page: Int,
        size: Int,
        radius: Int,
        latitude: Double,
        longitude: Double,
        sortType: String,
        roomTags: List<String>
    ) = runCatching {
        api.getTaxiPotList(filter, page, size, radius, latitude, longitude, sortType, roomTags)
    }.mapCatching { it.toDomain() }

    override suspend fun getTaxiPotListIntegration(
        filter: Map<String, Long>,
        radius: Int,
        latitude: Double,
        longitude: Double,
        sortType: String,
        roomTags: List<String>,
        isImminent : Boolean
    ): Result<TaxiPotListData> = runCatching {
        api.getTaxiPotListIntegration(filter, radius, latitude, longitude, sortType, roomTags, isImminent)
    }.mapCatching { it.toDomain() }

    override suspend fun getTaxiPotPreview(id: Long): Result<TaxiPotPreviewData> = runCatching {
        api.getTaxiPotPreview(id)
    }.mapCatching { it.toDomain() }

    override suspend fun getNearSpot(
        count: Int,
        latitude: Double,
        longitude: Double
    ): Result<NearSpotData> = runCatching {
        api.getNearSpot(count, latitude, longitude)
    }.mapCatching { it.toDomain() }

    override suspend fun getSpotList(
        page: Int,
        size: Int,
        searchLongitude: Double,
        searchLatitude: Double
    ): Result<SpotListData> = runCatching {
        api.getSpotList(page, size, searchLongitude, searchLatitude)
    }.mapCatching { it.toDomain() }

    override suspend fun createTaxiPot(
        spotId: Long,
        roomTagBitMask: List<String>,
        departureLongitude: Double,
        departureLatitude: Double,
        departureTime: String,
        departureName: String,
        wishHeadcount: Int
    ): Result<TaxiPotDetailData> = runCatching {
        api.createTaxiPot(
            CreateTaxiPotRequest(
                spotId,
                roomTagBitMask,
                departureLongitude,
                departureLatitude,
                departureTime,
                departureName,
                wishHeadcount
            )
        )
    }.mapCatching { it.toDomain() }

    override suspend fun getTaxiPotDetail(roomId: Long): Result<TaxiPotDetailData> =
        runCatching {
            api.getTaxiPotDetail(roomId)
        }.mapCatching { it.toDomain() }

    override suspend fun approveEnterTaxiPot(roomId: Long, memberId: Long): Result<Unit> =
        runCatching {
            api.approveEnterTaxiPot(roomId, memberId)
        }

    override suspend fun enterTaxiPot(roomId: Long): Result<Unit> = runCatching {
        api.enterTaxiPot(roomId)
    }

    override suspend fun getTaxiPotParticipants(roomId: Long): Result<TaxiPotParticipantsData> =
        runCatching {
            api.getTaxiPotParticipants(roomId)
        }.mapCatching { it.toDomain() }

    override suspend fun getTaxiPotWaitingMembers(roomId: Long): Result<TaxiPotWaitingMembersData> =
        runCatching {
            api.getTaxiPotWaitingMembers(roomId)
        }.mapCatching { it.toDomain() }

    override suspend fun getMemberDetail(memberId: Long): Result<MemberDetailData> =
        runCatching {
            api.getMemberDetail(memberId)
        }.mapCatching { it.toDomain() }
}