package com.motax.modutaxi.data.model.request

data class CreateTaxiPotRequest(
    val spotId: Long,
    val roomTagBitMask: List<String>,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val departureTime: String,
    val departureName: String,
    val wishHeadcount: Int
)
