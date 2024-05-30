package com.motax.modutaxi.presentation.ui.main.createparty.arrival

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.createparty.mapper.toUiMarkerItem
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiMarkerItem
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

data class ArrivalMapUiState(
    val markerDataList: List<UiMarkerItem> = emptyList(),
    val selectedMarkerData: UiMarkerItem = UiMarkerItem(),
    val searchKeyWord: String = ""
)

sealed class ArrivalMapEvent {
    data object SetMarkers : ArrivalMapEvent()
    data class SelectMarker(val latitude: Double, val longitude: Double) : ArrivalMapEvent()
    data class SelectArrival(
        val spotId: Long,
        val name: String
    ) : ArrivalMapEvent()
    data object NavigateToBack: ArrivalMapEvent()
}

@HiltViewModel
class ArrivalMapViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArrivalMapUiState())
    val uiState: StateFlow<ArrivalMapUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ArrivalMapEvent>()
    val event: SharedFlow<ArrivalMapEvent> = _event.asSharedFlow()

    fun getMarkerData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.getSpot(2000, latitude, longitude).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        markerDataList = uiState.value.markerDataList + it.spots.map { data ->
                            data.toUiMarkerItem()
                        }
                    )
                }
                _event.emit(ArrivalMapEvent.SetMarkers)
            }.onFailure {

            }
        }
    }

    fun setSearchKeyWord(keyword: String){
        _uiState.update { state ->
            state.copy(
                searchKeyWord = keyword
            )
        }
    }

    fun selectMarker(marker: UiMarkerItem) {
        _uiState.update { state ->
            state.copy(
                selectedMarkerData = marker
            )
        }

        viewModelScope.launch {
            _event.emit(ArrivalMapEvent.SelectMarker(marker.latitude, marker.longitude))
        }
    }

    fun selectArrival() {
        viewModelScope.launch {
            _event.emit(ArrivalMapEvent.SelectArrival(
                uiState.value.selectedMarkerData.spotId,
                uiState.value.selectedMarkerData.landMark.ifBlank { uiState.value.selectedMarkerData.address }
            ))
        }
    }

    fun navigateToBack(){
        viewModelScope.launch {
            _event.emit(ArrivalMapEvent.NavigateToBack)
        }
    }
}