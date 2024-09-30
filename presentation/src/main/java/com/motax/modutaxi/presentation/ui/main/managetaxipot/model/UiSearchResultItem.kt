package com.motax.modutaxi.presentation.ui.main.managetaxipot.model

data class UiSearchResultItem(
    val placeName: String="",
    val placeAddress: String="",
    val distance: Double = 0.0,
    val keyword: String="",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val selectLocation: (Double, Double, String, String) -> Unit
)