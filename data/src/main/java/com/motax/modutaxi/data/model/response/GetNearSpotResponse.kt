package com.motax.modutaxi.data.model.response

data class GetNearSpotResponse(
    val distance: Double,
    val spots: List<GetNearSpotItem>
)

data class GetNearSpotItem(
    val id: Long,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double
)
