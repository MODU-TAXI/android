package com.motax.modutaxi.presentation.chatmanager.model


data class ChatMessage(
    val roomId: Long,
    val dateTime: String,
    val content: String,
    val sender: String,
    val memberId: String,
    val messageType: String,
    val imageUrl: String?
)