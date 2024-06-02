package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.remote.MainApi
import com.motax.modutaxi.domain.model.GetSpotListData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {

    override suspend fun getTaxiPotList(page: Int, size: Int): Result<TaxiPotListData> =
        runCatching {
            api.getTaxiPotList(page, size)
        }.mapCatching { it.toDomain() }

    override suspend fun enterPot(roomId: Long): Result<Unit> = runCatching {
        api.enterPot(roomId)
    }

    override suspend fun getSpot(
        radius: Long,
        latitude: Double,
        longitude: Double
    ): Result<GetSpotListData> = runCatching {
        api.getSpot(radius, latitude, longitude)
    }.mapCatching { it.toDomain() }

}