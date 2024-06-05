package com.motax.modutaxi.presentation.ui.main.showparty.mapper

import com.motax.modutaxi.domain.model.TaxiPotListRadiusItemData
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotMarkerItem


fun TaxiPotListRadiusItemData.toUiTaxiPotMarkerItem() = UiTaxiPotMarkerItem(
    roomId = id,
    latitude = departureLatitude,
    longitude = departureLongitude,
    spotName = spotName
)