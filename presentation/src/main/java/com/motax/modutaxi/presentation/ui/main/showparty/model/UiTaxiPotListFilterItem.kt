package com.motax.modutaxi.presentation.ui.main.showparty.model

import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag

data class UiTaxiPotListFilterItem(
    val filter: RoomTag = RoomTag.EMPTY,
    val spotName: String = "",
    val onFilterClickLister: (RoomTag) -> Unit,
    val onSpotClickListener: () -> Unit,
)
