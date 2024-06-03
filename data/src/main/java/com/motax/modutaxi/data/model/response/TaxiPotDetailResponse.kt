package com.motax.modutaxi.data.model.response

data class TaxiPotDetailResponse(
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
    val path: Path,
    val profileImageUrl: String?,
    val roomId: Long,
    val roomTagBitMaskList: List<String>,
    val spotId: Long,
    val wishHeadcount: Int
)

data class Path(
    val coordinateReferenceSystem: CoordinateReferenceSystem,
    val coordinates: List<Coordinate>,
    val type: String
)

data class CoordinateReferenceSystem(
    val type: String
)

data class Coordinate(
    val values: List<Int>
)