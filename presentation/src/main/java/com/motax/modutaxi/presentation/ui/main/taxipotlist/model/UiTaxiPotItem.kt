package com.motax.modutaxi.presentation.ui.main.taxipotlist.model

data class UiTaxiPotItem(
    val id: String = "",
    val title: String = "",
    val currentParticipantCount: Int = 0,
    val maxParticipantsLimit: Int = 0,
    val feePerPerson: Int = 0,
    val minutesAgoChat: Int = 0,
    val departureDatetime: String = "",
    val route: String = "",
    val categories: List<TaxiPotCategory> = emptyList()
)

enum class TaxiPotCategory {
    DEADLINE,
    STUDENT_VERIFICATION,
    FEMALES_ONLY,
    QUIET
}