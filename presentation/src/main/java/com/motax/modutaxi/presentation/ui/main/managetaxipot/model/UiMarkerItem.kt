package com.motax.modutaxi.presentation.ui.main.managetaxipot.model

data class UiMarkerItem(
    val spotId: Long = 0L,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val landMark: String = "",
    val address: String = ""
)
