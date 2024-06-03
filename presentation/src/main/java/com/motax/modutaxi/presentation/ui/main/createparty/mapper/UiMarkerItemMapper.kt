package com.motax.modutaxi.presentation.ui.main.createparty.mapper

import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.domain.model.SpotListItemData
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiMarkerItem


fun NearSpotItemData.toUiMarkerItem() = UiMarkerItem(
    spotId = id,
    latitude = latitude,
    longitude = longitude,
    landMark = name,
    address = address
)