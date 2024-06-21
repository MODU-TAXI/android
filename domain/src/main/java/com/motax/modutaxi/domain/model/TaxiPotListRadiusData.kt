package com.motax.modutaxi.domain.model

data class TaxiPotListRadiusData(
    val rooms: List<TaxiPotListRadiusItemData>
)

data class TaxiPotListRadiusItemData(
    val id: Long,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val spotName: String
)
