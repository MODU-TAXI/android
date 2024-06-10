package com.motax.modutaxi.presentation.ui.main.matchdetail.model

import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import com.naver.maps.geometry.LatLng

data class UiMatchDetailData(
    val roomTags: List<RoomTag> = emptyList(),
    val departureDate: String = "",
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalTime: String = "",
    val arrivalName: String = "",
    val wholeFee: String = "",
    val feePerPerson: String = "",
    val isMyRoom: Boolean = false,
    val isParticipate: Boolean = false,
    val isWaiting: Boolean = false,
    val headCount: String = "",
    val path: List<LatLng> = emptyList()
)

