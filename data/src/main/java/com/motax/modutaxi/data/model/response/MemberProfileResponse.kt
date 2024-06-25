package com.motax.modutaxi.data.model.response

data class MemberProfileResponse(
    val id: Int,
    val nickname: String,
    val matchingCount: Int,
    val imageUrl: String,
    val certified: Boolean
)
