package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData

interface AuthRepository {

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getMemberId(): Long?
    suspend fun getGender(): String?
    suspend fun getProfileImg(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)
    suspend fun putMemberId(memberId: Long)
    suspend fun putGender(gender: String)
    suspend fun putProfileImg(profileImg: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteMemberId()
    suspend fun deleteGender()
    suspend fun deleteProfileImg()
    suspend fun refreshToken(refreshToken: String): Result<AuthData>

}