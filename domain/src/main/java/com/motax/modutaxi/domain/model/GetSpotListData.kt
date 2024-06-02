package com.motax.modutaxi.domain.model

data class GetSpotListData(
    val spots: List<GetSpotListItemData>
)

data class GetSpotListItemData(
    val id: Long,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val distance: Double,
    val liked: Boolean
)
