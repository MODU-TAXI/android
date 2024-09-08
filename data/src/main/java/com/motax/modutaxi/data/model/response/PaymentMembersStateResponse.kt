package com.motax.modutaxi.data.model.response

data class PaymentMembersStateResponse(
    val participantList: List<PaymentMembersItem>
)

data class PaymentMembersItem(
    val id: Long,
    val nickName: String,
    val name: String,
    val imageUrl: String,
    val status: String,
    val me: Boolean
)
