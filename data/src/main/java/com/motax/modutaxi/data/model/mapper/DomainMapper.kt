package com.motax.modutaxi.data.model.mapper

import com.motax.modutaxi.data.model.response.AddressFromGeoResponse
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.CertificateResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.SearchResultItem
import com.motax.modutaxi.data.model.response.SearchResultListResponse
import com.motax.modutaxi.data.model.response.TaxiPotItem
import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import com.motax.modutaxi.domain.model.AddressFromGeoAreaData
import com.motax.modutaxi.domain.model.AddressFromGeoCodeData
import com.motax.modutaxi.domain.model.AddressFromGeoData
import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import com.motax.modutaxi.domain.model.AddressFromGeoLandData
import com.motax.modutaxi.domain.model.AddressFromGeoLandItemData
import com.motax.modutaxi.domain.model.AddressFromGeoRegionData
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.CertificateData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.model.SearchResultData
import com.motax.modutaxi.domain.model.SearchResultListData
import com.motax.modutaxi.domain.model.TaxiPotData
import com.motax.modutaxi.domain.model.TaxiPotListData

fun AuthResponse.toDomain() = AuthData(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun MemberCheckResponse.toDomain() = MemberCheckData(
    key = key ?: "",
    existent = existent
)

fun CertificateResponse.toDomain() = CertificateData(
    isConfirm = isConfirm
)

fun TaxiPotItem.toDomain() = TaxiPotData(
    roomId = roomId,
    spotId = spotId,
    roomTagBitMaskList = roomTagBitMaskList,
    departureLongitude = departureLongitude,
    departureLatitude = departureLatitude,
    departureTime = departureTime,
    wishHeadcount = wishHeadcount,
    duration = duration,
    expectedCharge = expectedCharge
)

fun TaxiPotListResponse.toDomain() = TaxiPotListData(
    page = page,
    haxNext = haxNext,
    result = result.map { it.toDomain() }
)

fun SearchResultItem.toDomain() = SearchResultData(
    title = title,
    roadAddress = roadAddress,
    mapX = mapX,
    mapY = mapY
)

fun SearchResultListResponse.toDomain() = SearchResultListData(
    result = result.map { it.toDomain() }
)

fun AddressFromGeoResponse.toDomain() = AddressFromGeoData(
    results = results.map {
        AddressFromGeoItemData(
            name = it.name,
            code = AddressFromGeoCodeData(
                id = it.code.id,
                mappingId = it.code.mappingId,
                type = it.code.type
            ),
            region = AddressFromGeoRegionData(
                area0 = AddressFromGeoAreaData(name = it.region.area0.name),
                area1 = AddressFromGeoAreaData(name = it.region.area1.name),
                area2 = AddressFromGeoAreaData(name = it.region.area2.name),
                area3 = AddressFromGeoAreaData(name = it.region.area3.name),
                area4 = AddressFromGeoAreaData(name = it.region.area4.name),
            ),
            land = AddressFromGeoLandData(
                type = it.land.type,
                number1 = it.land.number1,
                number2 = it.land.number2,
                addition0 = AddressFromGeoLandItemData(
                    type = it.land.addition0.type,
                    value = it.land.addition0.value
                ),
                addition1 = AddressFromGeoLandItemData(
                    type = it.land.addition1.type,
                    value = it.land.addition1.value
                ),
                addition2 = AddressFromGeoLandItemData(
                    type = it.land.addition2.type,
                    value = it.land.addition2.value
                ),
                addition3 = AddressFromGeoLandItemData(
                    type = it.land.addition3.type,
                    value = it.land.addition3.value
                ),
                addition4 = AddressFromGeoLandItemData(
                    type = it.land.addition4.type,
                    value = it.land.addition4.value
                ),
                name = it.land.name
            )
        )
    }
)