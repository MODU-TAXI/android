package com.motax.modutaxi.presentation.ui.main.matchdetail.model

import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import com.naver.maps.geometry.LatLng

data class UiMatchDetailData(
    val managerId: Long = 0,
    val roomTags: List<RoomTag> = emptyList(),
    val departureDate: String = "",
    val departureTime: String = "",
    val departureName: String = "",
    val arrivalTime: String = "",
    val arrivalName: String = "",
    val minLatitude: Double = 0.0,
    val minLongitude: Double = 0.0,
    val maxLatitude: Double = 0.0,
    val maxLongitude: Double = 0.0,
    val wholeFee: String = "",
    val feePerPerson: String = "",
    val isMyRoom: Boolean = false,
    val isParticipate: Boolean = false,
    val isWaiting: Boolean = false,
    val headCount: String = "",
    val path: List<LatLng> = emptyList()
)

