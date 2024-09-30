package com.motax.modutaxi.presentation.ui.main.managetaxipot.mapper

import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.presentation.ui.main.managetaxipot.model.UiArrivalSpotItem


fun NearSpotItemData.toUiArrivalSpotItem(
    selectSpot : (Long, String, Double, Double) -> Unit
) = UiArrivalSpotItem(
    spotId = id,
    name = name,
    address = address,
    latitude= latitude,
    longitude= longitude,
    selectSpot = selectSpot
)