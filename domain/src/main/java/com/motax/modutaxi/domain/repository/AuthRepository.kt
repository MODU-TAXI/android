package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData

interface AuthRepository {

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun refreshToken(refreshToken: String): Result<AuthData>
}