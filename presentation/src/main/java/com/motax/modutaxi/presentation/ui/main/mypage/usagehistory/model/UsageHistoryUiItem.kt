package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model

data class UsageHistoryUiItem(
    val historyId: Long = -1L,
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val portionCharge: String = "",
    val navigateToUsageDetail: (Long) -> Unit
)
