package com.motax.modutaxi.domain.model

data class SpotListData(
    val spots: List<SpotListItemData>
)

data class SpotListItemData(
    val id: Long,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val distance: Double,
    val liked: Boolean
)
