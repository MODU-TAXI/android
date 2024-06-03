package com.motax.modutaxi.presentation.ui.main.matchdetail.model

data class UiMatchDetailData(
    val managerId: Long = 0,
    val roomId: Long = 0,
    val profileImageUrl: String = "",
    val departureDairyDate: String = "",
    val arrivalTime: String = "",
    val arrivalName: String = "",
    val departureName: String = "",
    val departureTime: String = "",
    val expectedChargePerPerson: Int = 0,
    val expectedCharge: Int = 0,
    val myRoom: Boolean = false,
    val participate: Boolean = false,
    val currentHeadcount: Int = 0,
    val wishHeadcount: Int = 0,
    val chipItems: List<String> = emptyList(),
    val participantList: List<UiParticipantItem> = emptyList()
)
