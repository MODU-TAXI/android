package com.motax.modutaxi.presentation.ui.main.createparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.getCurHour
import com.motax.modutaxi.presentation.ui.getCurMinute
import com.motax.modutaxi.presentation.ui.getTodayDate
import com.motax.modutaxi.presentation.ui.getUTCTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreatePartyUiState(
    val departureName: String = "",
    val arrivalName: String = "",
    val departureTime: String = "",
    val departureHour: Int = getCurHour(),
    val departureMinute: Int = getCurMinute(),
    val studentCertificationRoomTag: Boolean = false,
    val onlyWomanRoomTag: Boolean = false,
    val mannerRoomTag: Boolean = false,
    val todayDate: String = getTodayDate()
)

sealed class CreatePartyEvent {
    data class ShowTimePicker(val hour: Int, val minute: Int) : CreatePartyEvent()
    data object NavigateToDepartureMap : CreatePartyEvent()
    data object NavigateToArrivalSearch : CreatePartyEvent()
    data class NavigateToMatchDetail(val id: Long) : CreatePartyEvent()
    data class ShowToast(val msg: String) : CreatePartyEvent()
}

@HiltViewModel
class CreatePartyViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePartyUiState())
    val uiState: StateFlow<CreatePartyUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<CreatePartyEvent>()
    val event: SharedFlow<CreatePartyEvent> = _event.asSharedFlow()

    val spotId = MutableStateFlow(0L)
    val roomTagBitMask = MutableStateFlow<List<String>>(emptyList())
    val departureLongitude = MutableStateFlow(0.0)
    val departureLatitude = MutableStateFlow(0.0)
    val departureTime = MutableStateFlow("")
    val departureName = MutableStateFlow("")
    val wishHeadCount = MutableStateFlow(WishHeadCount.EMPTY)

    val isDataReady = combine(
        spotId,
        departureName,
        departureTime,
        wishHeadCount
    ) { spot, departureName, departureTime, wishHeadCount ->
        spot != 0L && departureName.isNotBlank() && departureTime.isNotBlank() && wishHeadCount != WishHeadCount.EMPTY
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun createTaxiPot() {
        viewModelScope.launch {
            val roomTag = mutableListOf<String>()
            if (uiState.value.studentCertificationRoomTag) roomTag.add(RoomTag.STUDENT_CERTIFICATION.text)
            if (uiState.value.onlyWomanRoomTag) roomTag.add(RoomTag.ONLY_WOMAN.text)
            if (uiState.value.mannerRoomTag) roomTag.add(RoomTag.MANNER.text)

            repository.createTaxiPot(
                spotId.value,
                roomTag,
                departureLongitude.value,
                departureLatitude.value,
                departureTime.value,
                departureName.value,
                wishHeadCount.value.count
            ).onSuccess {
                _event.emit(CreatePartyEvent.NavigateToMatchDetail(it.roomId))
            }.onFailure {

                _event.emit(CreatePartyEvent.ShowToast(it.message.toString()))
            }
        }
    }

    fun setDepartureInfo(
        latitude: Double,
        longitude: Double,
        name: String
    ) {
        departureLatitude.value = latitude
        departureLongitude.value = longitude
        departureName.value = name

        _uiState.update { state ->
            state.copy(
                departureName = name
            )
        }
    }

    fun setArrivalInfo(
        id: Long,
        name: String
    ) {
        spotId.value = id

        _uiState.update { state ->
            state.copy(
                arrivalName = name
            )
        }
    }

    fun showTimePicker() {
        viewModelScope.launch {
            _event.emit(
                CreatePartyEvent.ShowTimePicker(
                    uiState.value.departureHour,
                    uiState.value.departureMinute
                )
            )
        }
    }

    fun setDepartureTime(hour: Int, minute: Int) {
        _uiState.update { state ->
            state.copy(
                departureHour = hour,
                departureMinute = minute,
                departureTime = if (hour >= 12) "오후 " + "${hour % 12}시 ${minute}분" else "오전 " + "${hour % 12}시 ${minute}분"
            )
        }

        departureTime.value = getUTCTime(hour, minute)
    }

    fun navigateToDepartureMap() {
        viewModelScope.launch {
            _event.emit(CreatePartyEvent.NavigateToDepartureMap)
        }
    }

    fun navigateToArrivalSearch() {
        viewModelScope.launch {
            _event.emit(CreatePartyEvent.NavigateToArrivalSearch)
        }
    }

    fun selectWishHeadCount(count: WishHeadCount) {
        wishHeadCount.value = count
    }

    fun selectRoomTag(tag: RoomTag) {
        when (tag) {
            RoomTag.STUDENT_CERTIFICATION -> _uiState.update { state ->
                state.copy(studentCertificationRoomTag = !uiState.value.studentCertificationRoomTag)
            }

            RoomTag.ONLY_WOMAN -> _uiState.update { state ->
                state.copy(onlyWomanRoomTag = !uiState.value.onlyWomanRoomTag)
            }

            RoomTag.MANNER -> _uiState.update { state ->
                state.copy(mannerRoomTag = !uiState.value.mannerRoomTag)
            }

            else -> {

            }
        }
    }

}

enum class WishHeadCount(val count: Int) {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3)
}

enum class RoomTag(val text: String, val uiText: String) {
    EMPTY("",""),
    ONLY_WOMAN("ONLY_WOMAN","여자만"),
    ONLY_MAN("ONLY_MAN","남자만"),
    MANNER("MANNER","매너탑승"),
    QUIET("QUIET","조용한"),
    STUDENT_CERTIFICATION("STUDENT_CERTIFICATION","학생인증")
}
