package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.GetSpotListData
import com.motax.modutaxi.domain.model.TaxiPotListData

interface MainRepository {

    suspend fun getTaxiPotList(
        page: Int,
        size: Int
    ): Result<TaxiPotListData>

    suspend fun enterPot(
        roomId: Long
    ): Result<Unit>

    suspend fun getSpotRadius(
        radius: Long,
        latitude: Double,
        longitude: Double
    ): Result<GetSpotListData>

    suspend fun getSpotList(
        page: Int,
        size: Int,
        searchLongitude: Double,
        searchLatitude: Double
    ): Result<GetSpotListData>
}