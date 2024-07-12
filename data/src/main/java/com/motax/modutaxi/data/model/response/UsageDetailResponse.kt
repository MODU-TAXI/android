package com.motax.modutaxi.data.model.response

data class UsageDetailResponse(
    val managerId: Long,
    val historyId: Long,
    val roomId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val totalCharge: Int,
    val portionCharge: Int,
    val paymentMemberListResponse: PaymentMemberListResponse
)

data class PaymentMemberListResponse(
    val participantList: List<UsageParticipantResponse>
)

data class UsageParticipantResponse(
    val id: Long,
    val nickName: String,
    val name: String,
    val imageUrl: String?,
    val status: String,
    val me: Boolean,
    val portionCharge: Int
)