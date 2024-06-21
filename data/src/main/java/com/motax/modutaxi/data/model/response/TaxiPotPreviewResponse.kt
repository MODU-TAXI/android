package com.motax.modutaxi.data.model.response

data class TaxiPotPreviewResponse(
    val roomId: Long,
    val departureTime: String,
    val departureName: String,
    val arrivalName: String,
    val roomStatus: String,
    val currentHeadcount: Int,
    val wishHeadcount: Int,
    val expectedChargePerPerson: Int,
    val expectedCharge: Int
)
