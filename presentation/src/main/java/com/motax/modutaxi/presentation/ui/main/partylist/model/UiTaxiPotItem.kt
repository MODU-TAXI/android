package com.motax.modutaxi.presentation.ui.main.partylist.model

data class UiTaxiPotItem(
    val roomId: Long = -1L,
    val curHeadCount: Int = 0,
    val wishHeadCount: Int = 0,
    val feePerPerson: String = "",
    val recentChatTime: String = "",
    val departureDatetime: String = "",
    val departureSpot: String = "",
    val arrivalSpot: String = "",
    val categories: List<TaxiPotCategory> = emptyList(),
    val navigateToMatchDetail: (Long) -> Unit
)

enum class TaxiPotCategory {
    DEADLINE,
    STUDENT_VERIFICATION,
    FEMALES_ONLY,
    QUIET
}