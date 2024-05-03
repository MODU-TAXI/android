package com.motax.modutaxi.domain.model

data class TaxiPotListData(
    val page: Int,
    val haxNext: Boolean,
    val result: List<TaxiPotData>
)

data class TaxiPotData(
    val roomId: Long,
    val spotId: Long,
    val roomTagBitMaskList: List<String>,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val departureTime: String,
    val wishHeadcount: Int,
    val duration: Int,
    val expectedCharge: Int
)
