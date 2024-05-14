package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.remote.NaverMapApi
import com.motax.modutaxi.domain.model.AddressFromGeoData
import com.motax.modutaxi.domain.repository.NaverMapRepository
import javax.inject.Inject

class NaverMapRepositoryImpl @Inject constructor(
    private val api: NaverMapApi
): NaverMapRepository{

    override suspend fun getAddressFromGeo(
        request: String,
        coords: String,
        orders: String,
        output: String
    ): Result<AddressFromGeoData> = runCatching {  api.getAddressFromGeo(request, coords, orders, output) }.mapCatching {
        it.toDomain()
    }
}