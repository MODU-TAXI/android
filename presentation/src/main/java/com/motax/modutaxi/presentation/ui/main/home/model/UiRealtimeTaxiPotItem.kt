package com.motax.modutaxi.presentation.ui.main.home.model

import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag

data class UiRealtimeTaxiPotItem(
    val roomId: Long = -1L,
    val curHeadCount: Int = 0,
    val wishHeadCount: Int = 0,
    val feePerPerson: String = "",
    val departureSpot: String = "",
    val arrivalSpot: String = "",
    val departTime: String = "",
    val navigateToMatchDetail: (Long) -> Unit,
    val roomTags: List<String> = emptyList()
)