package com.motax.modutaxi.presentation.ui.main.matchdetail.model

data class UiWaitingMemberItem (
    val memberId: Long = 0,
    val profileImage: String = "",
    val nickname: String = "",
    val matchingCount: String = "",
    val certified: Boolean = false,
    val thisIsMe: Boolean = false,
    val acceptParticipant: (Long) -> Unit
)