package com.motax.modutaxi.presentation.ui.main.home.mapper

import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.presentation.ui.main.home.model.UiParticipatingTaxiPot

fun TaxiPotPreviewData.toUiParticipatingTaxiPot() = UiParticipatingTaxiPot(
    roomId = roomId,
    managerId = managerId,
    departureTime = departureTime,
    arrivalName = arrivalName,
    roomStatus = roomStatus,
    currentHeadCount = currentHeadcount,
    wishHeadCount = wishHeadcount,
    expectedChargePerPerson = expectedChargePerPerson
)