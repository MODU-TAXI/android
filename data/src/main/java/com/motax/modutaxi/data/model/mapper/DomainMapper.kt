package com.motax.modutaxi.data.model.mapper

import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.MemberCheckData

fun AuthResponse.toDomain() = AuthData(
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun MemberCheckResponse.toDomain() = MemberCheckData(
    key = key,
    existent = existent
)