package com.motax.modutaxi.presentation.ui.main.managetaxipot.mapper

import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.presentation.ui.main.managetaxipot.model.UiMarkerItem


fun NearSpotItemData.toUiMarkerItem() = UiMarkerItem(
    spotId = id,
    latitude = latitude,
    longitude = longitude,
    landMark = name,
    address = address
)