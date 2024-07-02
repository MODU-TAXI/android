package com.motax.modutaxi.domain.model

data class MonthlyUsageHistoryData(
    val year: Int,
    val month: Int,
    val totalCharge: Int,
    val accumulatePortionCharge: Int,
    val historyList: List<UsageHistoryDataItem>
)

data class UsageHistoryDataItem(
    val historyId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val portionCharge: Int
)