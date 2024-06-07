package com.motax.modutaxi.presentation.ui.main.matchdetail.model

data class UiWaitingMemberItem (
    val profileImage: String = "",
    val nickname: String = "",
    val matchingCount: String = "",
    val thisIsMe: Boolean = false,
    val acceptParticipant: (Long) -> Unit
)