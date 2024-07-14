package com.motax.modutaxi.data.model.response

data class NotificationResponse (
    val result: List<NotificationResponseItem>
)

data class NotificationResponseItem(
    val type: String,
    val message: String,
    val resourceId: Long,
    val dateTime: String,
    val checked: Boolean
)