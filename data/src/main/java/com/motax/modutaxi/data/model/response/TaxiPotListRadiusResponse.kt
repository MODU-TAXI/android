package com.motax.modutaxi.data.model.response

data class TaxiPotListRadiusResponse(
    val rooms: List<TaxiPotListRadiusItem>
)

data class TaxiPotListRadiusItem(
    val id: Long,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val spotName: String
)
