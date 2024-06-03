package com.motax.modutaxi.presentation.ui.main.matchdetail.mapper

import com.motax.modutaxi.domain.model.TaxiPotDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.MatchDetailUiState

fun TaxiPotDetailData.toUiRoomData() = MatchDetailUiState(
    managerId = managerId,
    profileImageUrl = profileImageUrl?: "",
    departureDairyDate = departureDairyDate,
    arrivalTime = arrivalTime,
    arrivalName = arrivalName,
    departureName = departureName,
    departureTime = departureTime,
    expectedChargePerPerson = expectedChargePerPerson,
    expectedCharge = expectedCharge,
    myRoom = myRoom,
    participate = participate,
    currentHeadcount = currentHeadcount,
    wishHeadcount = wishHeadcount,
    roomId = roomId,
)