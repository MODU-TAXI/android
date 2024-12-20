package com.motax.modutaxi.domain.model

// github test
data class TaxiPotParticipantsData(
    val inList: List<TaxiPotMemberData>
)

data class TaxiPotMemberData(
    val memberId: Long,
    val nickname: String,
    val imageUrl: String,
    val matchingCount: Int,
    val thisIsMe: Boolean,
    val certified: Boolean
)
