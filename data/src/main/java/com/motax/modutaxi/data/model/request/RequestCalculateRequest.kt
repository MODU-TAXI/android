package com.motax.modutaxi.data.model.request

data class RequestCalculateRequest(
    val roomId: Long,
    val accountId : Long,
    val totalCharge: Int,
    val participantList : List<UserItem>,
    val nonParticipantList: List<UserItem>
)

data class UserItem(
    val id: Long
)
