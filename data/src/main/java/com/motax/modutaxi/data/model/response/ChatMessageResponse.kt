package com.motax.modutaxi.data.model.response

data class ChatMessageResponse(
    val messages: List<ChatMessageItem>
)

data class ChatMessageItem(
    val roomId: Long,
    val messageType: String,
    val content: String,
    val sender: String,
    val memberId: String,
    val dateTime: String,
    val imageUrl: String?
)
