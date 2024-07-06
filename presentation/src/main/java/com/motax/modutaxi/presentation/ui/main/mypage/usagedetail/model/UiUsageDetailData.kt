package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model

data class UiUsageDetailData(
    val historyId: Long = 0,
    val roomId: Long = 0,
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val totalCharge: Int = 0,
    val portionCharge: Int = 0,
)