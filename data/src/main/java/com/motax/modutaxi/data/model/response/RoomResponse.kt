package com.motax.modutaxi.data.model.response

data class RoomResponse(
    val managerId: Int,
    val profileImageUrl: String,
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
    val path: Path,
    val participate: Boolean,
    val myRoom: Boolean
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