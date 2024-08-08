package com.motax.modutaxi.presentation.ui.main.chat.model

data class UiChatMessage(
    val type: Int = -1,
    val messageType: String = "",
    var profileImgUrl: String = "",
    val sender: String = "",
    val content: String = "",
    val imageUrl: String = "",
    var sentTime: String = "",
    val memberId: Long = 0,
    val dateTime: String = ""
)
