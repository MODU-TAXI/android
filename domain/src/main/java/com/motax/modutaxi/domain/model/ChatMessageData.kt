package com.motax.modutaxi.domain.model

data class ChatMessageData(
    val messages: List<ChatMessageItemData>
)

data class ChatMessageItemData(
    val roomId: Long,
    val messageType: String,
    val content: String,
    val sender: String,
    val memberId: String,
    val dateTime: String,
    val imageUrl: String?
)
