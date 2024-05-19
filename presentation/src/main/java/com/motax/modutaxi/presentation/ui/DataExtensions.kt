package com.motax.modutaxi.presentation.ui

import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


fun AddressFromGeoItemData.toAddressString() =
    region.area1.name + " " + region.area2.name + " " + region.area3.name + " " + land.name +
            land.number1 + if (land.number2.isNotBlank()) "-" + land.number2 else ""


fun AddressFromGeoItemData.toBuildingName() = land.addition0.value

fun getTodayDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return currentDate.format(formatter)
}

fun getCurHour(): Int {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.hour
}

fun getCurMinute(): Int {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.minute
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371e3 // 지구 반지름 (미터)
    val phi1 = lat1 * (PI / 180)
    val phi2 = lat2 * (PI / 180)
    val deltaPhi = (lat2 - lat1) * (PI / 180)
    val deltaLambda = (lon2 - lon1) * (PI / 180)

    val a = sin(deltaPhi / 2).pow(2) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // 미터 단위의 거리
}

