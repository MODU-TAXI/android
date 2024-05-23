package com.motax.modutaxi.presentation.ui.main.createparty.arrival

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.createparty.mapper.toUiMarkerItem
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiMarkerItem
import com.motax.modutaxi.presentation.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    val selectedMarkerData: UiMarkerItem = UiMarkerItem()
)

sealed class ArrivalMapEvent {
    data object SetMarkers : ArrivalMapEvent()
    data class SelectMarker(val latitude: Double, val longitude: Double): ArrivalMapEvent()
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
                Log.d(TAG,it.toString())
                _uiState.update { state ->
                    state.copy(
                        markerDataList = uiState.value.markerDataList + it.spots.map { data ->
                            data.toUiMarkerItem()
                        }
                    )
                }
                _event.emit(ArrivalMapEvent.SetMarkers)
            }.onFailure {
                Log.d(TAG,it.message.toString())
            }
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
}