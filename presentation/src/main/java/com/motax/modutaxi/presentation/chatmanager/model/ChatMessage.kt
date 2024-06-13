package com.motax.modutaxi.presentation.chatmanager.model

import java.time.LocalDateTime

data class ChatMessage(
    val roomId: Long,
    val dateTime: LocalDateTime,
    val content: String,
    val sender: String,
    val memberId: Long,
    val messageType: String
)