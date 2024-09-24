package com.motax.modutaxi.domain.model

data class TaxiPotListData(
    val result: List<TaxiPotListItemData>
)

data class TaxiPotListItemData(
    val roomId: Long,
    val spotId: Long,
    val arrivalTime: String,
    val arrivalName: String,
    val roomTagBitMaskList: List<String>,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val departureName: String,
    val departureTime: String,
    val wishHeadcount: Int,
    val currentHeadcount: Int,
    val durationMinutes: Int,
    val expectedChargePerPerson: Int,
    val expectedCharge: Int
)
