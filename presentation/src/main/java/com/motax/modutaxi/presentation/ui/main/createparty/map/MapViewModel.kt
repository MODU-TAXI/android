package com.motax.modutaxi.presentation.ui.main.createparty.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.NaverMapRepository
import com.motax.modutaxi.presentation.ui.toAddressString
import com.motax.modutaxi.presentation.ui.toBuildingName
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

data class MapUiState(
    val longitude: Double = 126.6538126,
    val latitude: Double = 37.4507292,
    val address: String = "",
    val landMark: String = "",
    val isPosition: Boolean = false,
    val isMoving: Boolean = false,
    val isSelectBtnEnable: Boolean = false
)

sealed class MapEvent {
    data object NavigateToSearch : MapEvent()
    data class SelectDeparture(val latitude: Double, val longitude: Double, val name: String, val address: String) :
        MapEvent()
}

sealed class TrackingState {
    data object TryOn : TrackingState()
    data object On : TrackingState()
    data object Off : TrackingState()
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val naverMapRepository: NaverMapRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<MapEvent>()
    val event: SharedFlow<MapEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _trackingState = MutableStateFlow<TrackingState>(TrackingState.TryOn)
    val trackingState: StateFlow<TrackingState> = _trackingState.asStateFlow()

    fun getAddressFromGeo(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            naverMapRepository.getAddressFromGeo(
                "coordsToaddr",
                "${longitude},${latitude}",
                "json",
                "roadaddr",
            ).onSuccess {
                val response = it.results
                if (response.isNotEmpty()) {
                    _uiState.update { state ->
                        state.copy(
                            address = response[0].toAddressString(),
                            landMark = response[0].toBuildingName(),
                            isPosition = false,
                            isSelectBtnEnable = true
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            address = "위치정보 없음",
                            landMark = "",
                            isPosition = false,
                            isSelectBtnEnable = false
                        )
                    }
                }
            }.onFailure {

            }
        }
    }

    fun changeMovingState(movingState: Boolean) {
        _uiState.update { state ->
            state.copy(
                isMoving = movingState
            )
        }

        if(movingState){
            _uiState.update { state ->
                state.copy(
                    isSelectBtnEnable = false
                )
            }
        }
    }

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(MapEvent.NavigateToSearch)
        }
    }

    fun selectLocationFromSearch(
        latitude: Double,
        longitude: Double
    ){
        _uiState.update { state ->
            state.copy(
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    fun selectDeparture() {
        viewModelScope.launch {
            _event.emit(
                MapEvent.SelectDeparture(
                    uiState.value.longitude,
                    uiState.value.latitude,
                    uiState.value.landMark,
                    uiState.value.address
                )
            )
        }
    }

    fun locationBtnClicked() {
        _trackingState.update {
            if (trackingState.value == TrackingState.Off) TrackingState.TryOn
            else TrackingState.Off
        }
    }

    fun trackingOn() {
        _trackingState.update {
            TrackingState.On
        }
    }

    fun trackingOff() {
        _trackingState.update {
            TrackingState.Off
        }
    }
}