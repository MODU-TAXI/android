package com.motax.modutaxi.presentation.ui.main.createparty.model

data class UiArrivalSpotItem(
    val spotId: Long = 0,
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val selectSpot: (Long, String, Double, Double) -> Unit
)
