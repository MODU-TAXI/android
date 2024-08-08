package com.motax.modutaxi.presentation.ui.main.home.model

data class UiParticipatingTaxiPot(
    val roomId: Long = -1L,
    val managerId: Long = -1L,
    val departureTime: String = "",
    val arrivalName: String = "",
    val roomStatus: String = "",
    val currentHeadCount: Int = 0,
    val wishHeadCount: Int = 0,
    val expectedChargePerPerson: Int = 0,
)
