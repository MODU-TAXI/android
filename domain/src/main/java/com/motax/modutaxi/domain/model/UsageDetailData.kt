package com.motax.modutaxi.domain.model

data class UsageDetailData(
    val managerId: Long,
    val historyId: Long,
    val roomId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val totalCharge: Int,
    val portionCharge: Int,
    val paymentMemberListData: PaymentMemberListData
)

data class PaymentMemberListData(
    val participantList: List<UsageParticipantData>
)

data class UsageParticipantData(
    val id: Long,
    val nickName: String,
    val name: String,
    val imageUrl: String?,
    val status: String,
    val me: Boolean,
    val portionCharge: Int
)