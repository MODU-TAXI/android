package com.motax.modutaxi.domain.model

data class MemberDetailData(
    val id: Long,
    val nickname: String,
    val matchingCount: Int,
    val imageUrl: String,
    val certified: Boolean
)