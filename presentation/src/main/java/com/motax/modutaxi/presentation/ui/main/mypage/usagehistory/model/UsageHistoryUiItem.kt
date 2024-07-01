package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model

data class UsageHistoryUiItem(
    val historyId: Int = -1,
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val portionCharge: Int
)
