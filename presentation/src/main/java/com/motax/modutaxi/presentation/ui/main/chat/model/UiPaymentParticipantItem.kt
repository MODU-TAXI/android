package com.motax.modutaxi.presentation.ui.main.chat.model

data class UiPaymentParticipantItem(
    val id: Long = -1,
    val profile: String = "",
    val name: String = "",
    val nickName: String = "",
    val state: String = "",
    val thisIsMe: Boolean = false,
)
