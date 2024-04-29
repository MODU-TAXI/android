package com.motax.modutaxi.data.model.mapper

import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.domain.model.AuthData


fun AuthResponse.toDomain() = AuthData(
    accessToken = accessToken,
    refreshToken = refreshToken
)