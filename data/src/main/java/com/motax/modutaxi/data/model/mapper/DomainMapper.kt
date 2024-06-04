package com.motax.modutaxi.data.model.mapper

import android.text.Html
import com.motax.modutaxi.data.model.response.AddressFromGeoResponse
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.CertificateResponse
import com.motax.modutaxi.data.model.response.GetNearSpotResponse
import com.motax.modutaxi.data.model.response.GetSpotListResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.MemberInfo
import com.motax.modutaxi.data.model.response.SearchResultItem
import com.motax.modutaxi.data.model.response.SearchResultListResponse
import com.motax.modutaxi.data.model.response.TaxiPotDetailResponse
import com.motax.modutaxi.data.model.response.TaxiPotItem
import com.motax.modutaxi.data.model.response.TaxiPotListRadiusResponse
import com.motax.modutaxi.data.model.response.TaxiPotListResponse
import com.motax.modutaxi.data.model.response.TokenResponse
import com.motax.modutaxi.domain.model.AddressFromGeoAreaData
import com.motax.modutaxi.domain.model.AddressFromGeoCodeData
import com.motax.modutaxi.domain.model.AddressFromGeoData
import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import com.motax.modutaxi.domain.model.AddressFromGeoLandData
import com.motax.modutaxi.domain.model.AddressFromGeoLandItemData
import com.motax.modutaxi.domain.model.AddressFromGeoRegionData
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.CertificateData
import com.motax.modutaxi.domain.model.CoordinateData
import com.motax.modutaxi.domain.model.CoordinateReferenceSystemData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.model.MemberInfoData
import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.domain.model.PathData
import com.motax.modutaxi.domain.model.SearchResultData
import com.motax.modutaxi.domain.model.SearchResultListData
import com.motax.modutaxi.domain.model.SpotListData
import com.motax.modutaxi.domain.model.SpotListItemData
import com.motax.modutaxi.domain.model.TaxiPotData
import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.domain.model.TaxiPotListData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusData
import com.motax.modutaxi.domain.model.TaxiPotListRadiusItemData
import com.motax.modutaxi.domain.model.TokenData

fun TokenResponse.toDomain() = TokenData(
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
    title = Html.fromHtml(title).toString(),
    roadAddress = roadAddress,
    mapx = mapx,
    mapy = mapy
)

fun SearchResultListResponse.toDomain() = SearchResultListData(
    results = items.map { it.toDomain() }
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

fun GetNearSpotResponse.toDomain() = NearSpotData(
    distance = distance,
    spots = spots.map {
        NearSpotItemData(
            it.id,
            it.name,
            it.address,
            it.longitude,
            it.latitude
        )
    }
)

fun GetSpotListResponse.toDomain() = SpotListData(spots.map {
    SpotListItemData(
        it.id,
        it.name,
        it.address,
        it.longitude,
        it.latitude,
        it.distance,
        it.liked
    )
})

fun AuthResponse.toDomain() = AuthData(
    tokenData = tokenResponse.toDomain(),
    memberInfoData = memberInfoResponse.toDomain()
)

fun MemberInfo.toDomain() = MemberInfoData(
    id = id,
    name = name,
    nickname = nickname ?: "",
    gender = gender,
    phoneNumber = phoneNumber,
    email = email ?: "",
    imageUrl = imageUrl
)

fun TaxiPotDetailResponse.toDomain() = TaxiPotDetailData(
    arrivalLatitude = arrivalLatitude,
    arrivalLongitude = arrivalLongitude,
    arrivalName = arrivalName,
    arrivalTime = arrivalTime,
    currentHeadcount = currentHeadcount,
    departureDairyDate = departureDairyDate,
    departureLatitude = departureLatitude,
    departureLongitude = departureLongitude,
    departureName = departureName,
    departureTime = departureTime,
    durationMinutes = durationMinutes,
    expectedCharge = expectedCharge,
    expectedChargePerPerson = expectedChargePerPerson,
    managerId = managerId,
    myRoom = myRoom,
    participate = participate,
    path = PathData(
        coordinateReferenceSystem = CoordinateReferenceSystemData(path.coordinateReferenceSystem.type),
        coordinates = path.coordinates.map {
            CoordinateData(it.values)
        },
        type = path.type
    ),
    profileImageUrl = profileImageUrl,
    roomId = roomId,
    roomTagBitMaskList = roomTagBitMaskList,
    spotId = spotId,
    wishHeadcount = wishHeadcount
)

fun TaxiPotListRadiusResponse.toDomain() = TaxiPotListRadiusData(
    rooms = rooms.map {
        TaxiPotListRadiusItemData(
            id = it.id,
            departureLongitude = it.departureLongitude,
            departureLatitude = it.departureLatitude,
            spotName = it.spotName
        )
    }
)
