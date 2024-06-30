package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model

data class UsageHistoryItem(
    val historyId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val portionCharge: Int
)
