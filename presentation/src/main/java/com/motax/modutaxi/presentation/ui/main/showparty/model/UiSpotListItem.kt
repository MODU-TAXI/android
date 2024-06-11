package com.motax.modutaxi.presentation.ui.main.showparty.model

data class UiSpotListItem(
    val spotId: Long = 0,
    val name: String = "",
    val address: String = "",
    val selectSpot: (Long, String) -> Unit
)
