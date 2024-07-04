package com.motax.modutaxi.domain.model

data class AuthData(
    val tokenData: TokenData,
    val memberInfoData: MemberInfoData
)

data class MemberInfoData(
    val id: Long,
    val name: String,
    val nickname: String,
    val gender: String,
    val phoneNumber: String,
    val email: String,
    val imageUrl: String,
    val matchingCount: Int,
    val blocked: Boolean
)
