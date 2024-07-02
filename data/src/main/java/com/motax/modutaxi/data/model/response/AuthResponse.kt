package com.motax.modutaxi.data.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val tokenResponse : TokenResponse,
    val memberInfoResponse : MemberInfo
)

data class TokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)

data class MemberInfo(
    val id: Long,
    val name: String,
    val nickname: String?,
    val gender: String,
    val phoneNumber: String,
    val email: String?,
    val imageUrl: String,
    val matchingCount: Int,
    val blocked: Boolean
)
