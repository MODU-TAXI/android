package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model

data class UsageHistoryItem(
    val historyId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val portionCharge: Int
)
