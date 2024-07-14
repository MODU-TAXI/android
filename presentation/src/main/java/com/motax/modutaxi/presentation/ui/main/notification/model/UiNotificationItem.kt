package com.motax.modutaxi.presentation.ui.main.notification.model

data class UiNotificationItem(
    val type: String,
    val message: String,
    val id: Long,
    val dateTime: String,
    val checked: Boolean
)
