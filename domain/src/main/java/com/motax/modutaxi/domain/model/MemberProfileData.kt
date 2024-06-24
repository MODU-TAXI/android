package com.motax.modutaxi.domain.model

data class MemberProfileData(
    val id: Int,
    val nickname: String,
    val matchingCount: Int,
    val imageUrl: String,
    val certified: Boolean
)
