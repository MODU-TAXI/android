package com.motax.modutaxi.data.model.response

data class GetSpotListResponse(
    val spots: List<GetSpotListItem>
)

data class GetSpotListItem(
    val id: Long,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val distance: Double,
    val liked: Boolean
)
