package com.motax.modutaxi.domain.model

data class TaxiPotPreviewData(
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
