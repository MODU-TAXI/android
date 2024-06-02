package com.motax.modutaxi.data.model.response

data class GetSpotListResponse(
    val spots: List<GetSpotListItem>
)

data class GetSpotListItem(
    val id: Long,
    val longitude: Double,
    val latitude: Double,
    val spotName: String?
)
