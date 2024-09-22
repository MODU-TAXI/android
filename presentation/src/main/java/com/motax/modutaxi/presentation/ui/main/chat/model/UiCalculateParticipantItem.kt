package com.motax.modutaxi.presentation.ui.main.chat.model

data class UiCalculateParticipantItem(
    val memberId: Long = 0,
    val profileImage: String = "",
    val nickname: String = "",
    val amount: String = "",
    val thisIsMe: Boolean = false,
    val isCalculate : Boolean = true,
    val changeParticipantState: (UiCalculateParticipantItem) -> Unit
)
