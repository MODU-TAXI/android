package com.motax.modutaxi.data.model.response

data class UsageHistoryResponse(
    val year: Int,
    val month: Int,
    val accumulateTotalCharge: Int,
    val accumulatePortionCharge: Int,
    val historySimpleListResponse: List<UsageHistoryItemResponse>
)

data class UsageHistoryItemResponse(
    val historyId: Int,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val portionCharge: Int
)