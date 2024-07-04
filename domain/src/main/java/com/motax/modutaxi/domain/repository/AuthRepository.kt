package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData

interface AuthRepository {

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getMemberId(): Long?
    suspend fun getMemberName(): String?
    suspend fun getMemberGender(): String?
    suspend fun getMemberPhoneNumber(): String?
    suspend fun getMemberEmail(): String?
    suspend fun getMatchingCount(): Int?
    suspend fun getMemberBlocked(): Boolean?
    suspend fun getProfileUrl(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)
    suspend fun putMemberId(memberId: Long)
    suspend fun putMemberName(name: String)
    suspend fun putMemberGender(gender: String)
    suspend fun putMemberPhoneNumber(phoneNumber: String)
    suspend fun putMemberEmail(email: String)
    suspend fun putMatchingCount(matchingCount: Int)
    suspend fun putMemberBlocked(blocked: Boolean)
    suspend fun putProfileUrl(url: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteMemberId()
    suspend fun deleteMemberName()
    suspend fun deleteMemberGender()
    suspend fun deleteMemberPhoneNumber()
    suspend fun deleteMemberEmail()
    suspend fun deleteMatchingCount()
    suspend fun deleteMemberBlocked()
    suspend fun deleteProfileUrl()

    suspend fun refreshToken(refreshToken: String): Result<AuthData>

}