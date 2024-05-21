package com.motax.modutaxi.presentation.ui.main.createparty.mapper

import com.motax.modutaxi.domain.model.SearchResultData
import com.motax.modutaxi.presentation.ui.calculateDistance
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiSearchResultItem
import com.motax.modutaxi.presentation.ui.toDistanceString



fun SearchResultData.toUiSearchResultItem(
    curLatitude: Double,
    curLongitude: Double,
    keyword: String,
    selectLocation: (Double, Double, String) -> Unit
): UiSearchResultItem {

    val distance = calculateDistance(
        curLongitude,
        curLatitude,
        mapx.toDouble() / 1e7,
        mapy.toDouble() / 1e7
    )

    return UiSearchResultItem(
        title,
        roadAddress,
        distance,
        keyword,
        mapy.toDouble() / 1e7,
        mapx.toDouble() / 1e7,
        selectLocation
    )
}