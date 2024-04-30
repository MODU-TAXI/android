package com.motax.modutaxi.presentation.adapters

data class Taxipot(
    val id: String,
    val title: String,
    val currentParticipantCount: Int,
    val maxParticipantsLimit: Int,
    val feePerPerson: Int,
    val minutesAgoChat: Int,
    val departureDatetime: String,
    val route: String,
    val categories: List<TaxipotCategory>
)

enum class TaxipotCategory {
    DEADLINE,
    STUDENT_VERIFICATION,
    FEMALES_ONLY,
    QUIET
}