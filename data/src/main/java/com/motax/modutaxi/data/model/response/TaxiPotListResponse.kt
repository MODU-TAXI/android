package com.motax.modutaxi.data.model.response


data class TaxiPotListResponse(
    val page: Int,
    val haxNext: Boolean,
    val result: List<TaxiPotItem>
)

data class TaxiPotItem(
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
