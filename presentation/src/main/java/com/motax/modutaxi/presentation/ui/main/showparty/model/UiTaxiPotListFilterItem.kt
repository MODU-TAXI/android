package com.motax.modutaxi.presentation.ui.main.showparty.model

import com.motax.modutaxi.presentation.ui.main.managetaxipot.RoomTag

data class UiTaxiPotListFilterItem(
    val filter: RoomTag = RoomTag.EMPTY,
    val isClicked: Boolean = false,
    val onFilterClickLister: (RoomTag) -> Unit,
)
