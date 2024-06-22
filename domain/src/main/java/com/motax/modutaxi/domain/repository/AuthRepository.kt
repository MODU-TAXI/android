package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData

interface AuthRepository {

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getMemberId(): String?
    suspend fun getMemberName(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)
    suspend fun putMemberId(id: String)
    suspend fun putMemberName(name: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteMemberId()
    suspend fun deleteMemberName()
    suspend fun refreshToken(refreshToken: String): Result<AuthData>
}