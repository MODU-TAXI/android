package com.motax.modutaxi.presentation.ui.main.createparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.presentation.ui.getCurHour
import com.motax.modutaxi.presentation.ui.getCurMinute
import com.motax.modutaxi.presentation.ui.getTodayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreatePartyUiState(
    val departureLongitude: Double = 0.0,
    val departureLatitude: Double = 0.0,
    val spotId: Long = 0,
    val departureName: String = "",
    val arrivalName: String = "",
    val departureTime: String = "",
    val departureHour: Int = getCurHour(),
    val departureMinute: Int = getCurMinute(),
    val wishHeadCount: WishHeadCount = WishHeadCount.EMPTY,
    val roomTag: RoomTag = RoomTag.EMPTY,
    val todayDate: String = getTodayDate()
)

sealed class CreatePartyEvent {
    data class ShowTimePicker(val hour: Int, val minute: Int) : CreatePartyEvent()
    data object NavigateToDepartureMap : CreatePartyEvent()
    data object NavigateToArrivalSearch : CreatePartyEvent()
}

@HiltViewModel
class CreatePartyViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePartyUiState())
    val uiState: StateFlow<CreatePartyUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<CreatePartyEvent>()
    val event: SharedFlow<CreatePartyEvent> = _event.asSharedFlow()


    fun setDepartureInfo(
        longitude: Double,
        latitude: Double,
        name: String
    ) {
        _uiState.update { state ->
            state.copy(
                departureLatitude = latitude,
                departureLongitude = longitude,
                departureName = name
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
        _uiState.update { state ->
            state.copy(
                wishHeadCount = count
            )
        }
    }

    fun selectRoomTag(tag: RoomTag) {
        _uiState.update { state ->
            state.copy(
                roomTag = tag
            )
        }
    }

}

enum class WishHeadCount(val count: Int) {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3)
}

enum class RoomTag(val text: String) {
    EMPTY(""),
    ONLY_WOMAN("ONLY_WOMAN"),
    ONLY_MAN("ONLY_MAN"),
    MANNER("MANNER"),
    QUIET("QUIET"),
    STUDENT_CERTIFICATION("STUDENT_CERTIFICATION")
}
