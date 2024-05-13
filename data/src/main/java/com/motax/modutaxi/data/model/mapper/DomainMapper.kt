package com.motax.modutaxi.data.model.mapper

import android.text.Html
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.CertificateResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.SearchResultItem
import com.motax.modutaxi.data.model.response.SearchResultListResponse
import com.motax.modutaxi.data.model.response.TaxiPotItem
import com.motax.modutaxi.data.model.response.TaxiPotListResponse
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
    title = Html.fromHtml(title).toString(),
    roadAddress = roadAddress
)

fun SearchResultListResponse.toDomain() = SearchResultListData(
    results = items.map { it.toDomain() }
)