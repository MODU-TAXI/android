package com.motax.modutaxi.data.model.response


data class TaxiPotListResponse(
    val page: Int,
    val hasNext: Boolean,
    val result: List<TaxiPotItem>
)

data class TaxiPotItem(
    val roomId: Long,
    val spotId: Long,
    val arrivalTime: String,
    val arrivalName: String,
    val roomTagBitMaskList: List<String>,
    val departureName: String,
    val departureTime: String,
    val wishHeadcount: Int,
    val currentHeadcount: Int,
    val durationMinutes: Int,
    val expectedChargePerPerson: Int,
    val expectedCharge: Int
)
