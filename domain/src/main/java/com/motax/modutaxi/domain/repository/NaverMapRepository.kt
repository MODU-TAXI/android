package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AddressFromGeoData

interface NaverMapRepository {

    suspend fun getAddressFromGeo(
        request: String,
        coords: String,
        orders: String,
        output: String
    ): Result<AddressFromGeoData>
}