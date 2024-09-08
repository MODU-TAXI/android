package com.motax.modutaxi.domain.model

data class PaymentMembersStateData(
    val participantList: List<PaymentMembersItemData>
)

data class PaymentMembersItemData(
    val id: Long,
    val nickName: String,
    val name: String,
    val imageUrl: String,
    val status: String,
    val me: Boolean
)

