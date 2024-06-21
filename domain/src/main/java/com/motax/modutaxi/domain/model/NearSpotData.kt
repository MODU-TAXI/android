package com.motax.modutaxi.domain.model

data class NearSpotData(
    val minLongitude: Double,
    val minLatitude: Double,
    val maxLongitude: Double,
    val maxLatitude: Double,
    val spots: List<NearSpotItemData>
)

data class NearSpotItemData(
    val id: Long,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double
)
