package com.motax.modutaxi.presentation.ui.main.matchdetail.mapper

import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiMatchDetailData
import com.motax.modutaxi.presentation.ui.toRoomTag

fun TaxiPotDetailData.toUiMatchDetailData() = UiMatchDetailData(
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
    headCount = "${currentHeadcount}/${wishHeadcount}"
)