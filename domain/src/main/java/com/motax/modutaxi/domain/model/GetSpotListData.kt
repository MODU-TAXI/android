package com.motax.modutaxi.domain.model

data class GetSpotListData(
    val spots: List<GetSpotListItemData>
)

data class GetSpotListItemData(
    val id: Long,
    val longitude: Double,
    val latitude: Double,
    val spotName: String?
)
