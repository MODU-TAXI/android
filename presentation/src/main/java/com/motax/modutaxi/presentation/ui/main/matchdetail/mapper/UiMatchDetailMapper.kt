package com.motax.modutaxi.presentation.ui.main.matchdetail.mapper

import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiMatchDetailData
import com.motax.modutaxi.presentation.ui.toRoomTag
import com.naver.maps.geometry.LatLng

fun TaxiPotDetailData.toUiMatchDetailData() = UiMatchDetailData(
    managerId = managerId,
    roomTags = roomTagBitMaskList.map { it.toRoomTag() },
    departureDate = departureDairyDate,
    departureTime = departureTime,
    departureName = departureName,
    arrivalTime = arrivalTime,
    arrivalName = arrivalName,
    wholeFee = "${expectedCharge.formatNumberWithCommas()}원",
    feePerPerson = "${expectedChargePerPerson.formatNumberWithCommas()}원",
    isMyRoom = myRoom,
    isParticipate = participate,
    isWaiting = waiting,
    headCount = "${currentHeadcount}/${wishHeadcount}",
    path = path.coordinates.map { LatLng(it.values[1], it.values[0]) },
    minLongitude = minLongitude,
    minLatitude = minLatitude,
    maxLongitude = maxLongitude,
    maxLatitude = maxLatitude
)