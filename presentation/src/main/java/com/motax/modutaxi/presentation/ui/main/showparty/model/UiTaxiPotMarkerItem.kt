package com.motax.modutaxi.presentation.ui.main.showparty.model

data class UiTaxiPotMarkerItem(
    val roomId: Long = 0,
    val spotId: Long = 0,
    val departureName: String = "",
    val departureTime: String = "",
    val arrivalName: String = "",
    val headCountString: String = "",
    val expectedCharge : String = "",
    val roomTagBitMaskList : List<String> = emptyList(),
)
