package com.motax.modutaxi.presentation.ui.main.showparty.model

import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag

data class UiTaxiPotListItem(
    val roomId: Long = -1L,
    val headCount: String = "",
    val departureLatitude: Double = 0.0,
    val departureLongitude: Double = 0.0,
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val recentChatTime: String = "",
    val feeForPerson: String = "",
    val roomTags: List<RoomTag> = emptyList(),
    val navigateToMatchDetail: (Long) -> Unit
)
