package com.motax.modutaxi.presentation.ui.main.createparty.search.model

data class UiSearchResultItem(
    val placeName: String="",
    val placeAddress: String="",
    val distance: String="",
    val keyword: String="",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val selectLocation: (Double, Double, String) -> Unit
)