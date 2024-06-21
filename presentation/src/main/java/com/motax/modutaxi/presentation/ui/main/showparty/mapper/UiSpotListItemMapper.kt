package com.motax.modutaxi.presentation.ui.main.showparty.mapper

import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiSpotListItem


fun NearSpotItemData.toUiSpotListItem(
    selectSpot: (Long, String) -> Unit
) = UiSpotListItem(
    spotId = id,
    name = name,
    address = address,
    selectSpot = selectSpot
)