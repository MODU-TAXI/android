package com.motax.modutaxi.presentation.ui

import com.motax.modutaxi.domain.model.AddressFromGeoItemData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun AddressFromGeoItemData.toAddressString() = region.area1.name + " " + region.area2.name + " " + region.area3.name + " " + land.name +
            land.number1 + if(land.number2.isNotBlank()) "-" + land.number2 else ""


fun AddressFromGeoItemData.toBuildingName() = land.addition0.value

fun getTodayDate(): String{
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return currentDate.format(formatter)
}

fun getCurHour(): Int{
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.hour
}

fun getCurMinute(): Int{
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.minute
}