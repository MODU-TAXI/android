package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model

data class UiUsageParticipantItem(
    val id: Long = -1L,
    val nickname: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val status: String = "",
    val me: Boolean = false,
    val onClickListener: (Long) -> Unit
)