package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.TaxiPotListData

interface MainRepository {

    suspend fun getTaxiPotList(
        page: Int,
        size : Int
    ): Result<TaxiPotListData>

    suspend fun enterPot(
        roomId : Long
    ): Result<Unit>
}