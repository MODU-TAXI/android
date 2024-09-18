package com.motax.modutaxi.domain.model

data class NotificationData(
    val page: Int,
    val hasNext: Boolean,
    val result: List<NotificationDataItem>
)

data class NotificationDataItem(
    val type: String,
    val message: String,
    val resourceId: Long,
    val dateTime: String,
    val checked: Boolean
)
