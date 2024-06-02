package com.motax.modutaxi.presentation.ui.main.createparty.mapper

import com.motax.modutaxi.domain.model.GetSpotListData
import com.motax.modutaxi.domain.model.GetSpotListItemData
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiMarkerItem


fun GetSpotListItemData.toUiMarkerItem() = UiMarkerItem(
    latitude = latitude,
    longitude = longitude,
    landMark = spotName ?: "주안역",
    address = "주소"
)