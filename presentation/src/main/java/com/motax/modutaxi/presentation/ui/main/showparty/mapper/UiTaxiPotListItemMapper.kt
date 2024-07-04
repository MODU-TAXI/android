package com.motax.modutaxi.presentation.ui.main.showparty.mapper

import com.motax.modutaxi.domain.model.TaxiPotListItemData
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.toRoomTag


fun TaxiPotListItemData.toUiTaxiPotListItem(
    navigateToMatchDetail: (Long) -> Unit
) = UiTaxiPotListItem(
    roomId = roomId,
    headCount = "${wishHeadcount}/${currentHeadcount}",
    departureLatitude = departureLatitude,
    departureLongitude = departureLongitude,
    departureTime = "출발 ${departureTime}",
    departureName = departureName,
    arrivalName = arrivalName,
    feeForPerson = "인당 ${expectedChargePerPerson.formatNumberWithCommas()} 원",
    roomTags = roomTagBitMaskList.map { it.toRoomTag() },
    navigateToMatchDetail = navigateToMatchDetail
)

fun TaxiPotPreviewData.toUiTaxiPotListItem(
    navigateToMatchDetail: (Long) -> Unit
) = UiTaxiPotListItem(
    roomId = roomId,
    headCount = "${wishHeadcount}/${currentHeadcount}",
    departureTime = "출발 ${departureTime}",
    departureName = if(departureName.length > 10) departureName.substring(0..10) + "..." else departureName,
    arrivalName = if(arrivalName.length > 10) arrivalName.substring(0..10) + "..." else arrivalName,
    feeForPerson = "인당 ${expectedChargePerPerson.formatNumberWithCommas()} 원",
    navigateToMatchDetail = navigateToMatchDetail
)