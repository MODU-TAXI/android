package com.motax.modutaxi.presentation.ui.main.createparty.mapper

import com.motax.modutaxi.domain.model.NearSpotData
import com.motax.modutaxi.domain.model.NearSpotItemData
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiArrivalSpotItem


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