package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData

interface MainRepository {

    suspend fun getTaxiPotList(
        page: Int,
        size: Int
    ): Result<TaxiPotListData>

    suspend fun enterPot(
        roomId: Long
    ): Result<Unit>

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

    suspend fun getRoom(
        roomId: Long
    ):Result<TaxiPotDetailData>
}