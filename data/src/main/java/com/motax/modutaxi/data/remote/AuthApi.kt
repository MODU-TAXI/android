package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.response.AuthResponse
import retrofit2.http.Header
import retrofit2.http.PATCH

interface AuthApi {

    @PATCH("/api/members/refresh")
    suspend fun refreshToken(
        @Header("refreshToken") refreshToken: String
    ): AuthResponse
}