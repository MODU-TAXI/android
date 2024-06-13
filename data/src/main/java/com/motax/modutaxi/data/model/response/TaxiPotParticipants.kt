package com.motax.modutaxi.data.model.response

data class TaxiPotParticipants(
    val inList: List<TaxiPotMemberItem>
)

data class TaxiPotMemberItem(
    val memberId: Long,
    val nickname: String,
    val imageUrl: String,
    val matchingCount: Int,
    val thisIsMe: Boolean,
    val certified: Boolean
)
