package com.motax.modutaxi.data.model.mapper

import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.SmsResponse
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.model.SmsData

fun AuthResponse.toDomain() = AuthData(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun MemberCheckResponse.toDomain() = MemberCheckData(
    key = key,
    existent = existent
)

fun SmsResponse.toDomain() = SmsData(
    isConfirm = isConfirm
)