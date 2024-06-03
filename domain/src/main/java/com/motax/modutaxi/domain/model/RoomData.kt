package com.motax.modutaxi.domain.model

data class RoomData(
    val managerId: Int,
    val profileImageUrl: String?,
    val roomId: Int,
    val spotId: Int,
    val departureDairyDate: String,
    val arrivalLongitude: Double,
    val arrivalLatitude: Double,
    val arrivalTime: String,
    val arrivalName: String,
    val roomTagBitMaskList: List<String>,
    val departureLongitude: Double,
    val departureLatitude: Double,
    val departureTime: String,
    val departureName: String,
    val currentHeadcount: Int,
    val wishHeadcount: Int,
    val durationMinutes: Int,
    val expectedChargePerPerson: Int,
    val expectedCharge: Int,
    val path: PathData,
    val participate: Boolean,
    val myRoom: Boolean
)

data class PathData(
    val coordinates: List<CoordinateData>,
    val type: String
)

data class CoordinateData(
    val values: List<Double>
)