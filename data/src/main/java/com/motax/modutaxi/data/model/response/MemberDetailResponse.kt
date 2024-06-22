package com.motax.modutaxi.data.model.response

data class MemberDetailResponse(
    val id: Long,
    val nickname: String,
    val matchingCount: Int,
    val imageUrl: String,
    val certified: Boolean
)
