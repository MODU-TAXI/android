package com.motax.modutaxi.domain.model

data class TaxiPotDetailData(
    val arrivalLatitude: Double,
    val arrivalLongitude: Double,
    val arrivalName: String,
    val arrivalTime: String,
    val currentHeadcount: Int,
    val departureDairyDate: String,
    val departureLatitude: Double,
    val departureLongitude: Double,
    val departureName: String,
    val departureTime: String,
    val durationMinutes: Int,
    val expectedCharge: Int,
    val expectedChargePerPerson: Int,
    val managerId: Long,
    val myRoom: Boolean,
    val participate: Boolean,
    val path: PathData,
    val profileImageUrl: String?,
    val roomId: Long,
    val roomTagBitMaskList: List<String>,
    val spotId: Long,
    val wishHeadcount: Int
)

data class PathData(
    val coordinateReferenceSystem: CoordinateReferenceSystemData,
    val coordinates: List<CoordinateData>,
    val type: String
)

data class CoordinateReferenceSystemData(
    val type: String
)

data class CoordinateData(
    val values: List<Int>
)
