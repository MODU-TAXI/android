package com.motax.modutaxi.presentation.ui.main.managetaxipot.departure

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.NaverMapRepository
import com.motax.modutaxi.presentation.ui.toAddressString
import com.motax.modutaxi.presentation.ui.toBuildingName
import com.motax.modutaxi.presentation.util.Constants.TAG
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

data class DepartureMapUiState(
    val longitude: Double = 126.6538126,
    val latitude: Double = 37.4507292,
    val isFromSearch: Boolean = false,
    val address: String = "",
    val landMark: String = "",
    val isPosition: Boolean = false,
    val isMoving: Boolean = false,
    val isSelectBtnEnable: Boolean = false
)

sealed class DepartureMapEvent {
    data object NavigateToSearch : DepartureMapEvent()
    data class SelectDeparture(
        val latitude: Double,
        val longitude: Double,
        val name: String,
        val address: String
    ) : DepartureMapEvent()

    data object MoveToCurLocation : DepartureMapEvent()
    data object NavigateToBack : DepartureMapEvent()
}


@HiltViewModel
class DepartureMapViewModel @Inject constructor(
    private val naverMapRepository: NaverMapRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<DepartureMapEvent>()
    val event: SharedFlow<DepartureMapEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(DepartureMapUiState())
    val uiState: StateFlow<DepartureMapUiState> = _uiState.asStateFlow()

    fun getAddress(latitude: Double, longitude: Double) {
        getAddressFromGeo(latitude, longitude)
        getAddressFromServer(latitude, longitude)
    }

    private fun getAddressFromGeo(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            naverMapRepository.getAddressFromGeo(
                "coordsToaddr",
                "${longitude},${latitude}",
                "json",
                "roadaddr",
            ).onSuccess {
                val response = it.results
                _uiState.update { state ->
                    state.copy(
                        isFromSearch = false
                    )
                }

                if (response.isNotEmpty()) {
                    _uiState.update { state ->
                        state.copy(
                            latitude = latitude,
                            longitude = longitude,
                            address = response[0].toAddressString(),
                            landMark = response[0].toBuildingName(),
                            isPosition = false,
                            isSelectBtnEnable = true
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            latitude = latitude,
                            longitude = longitude,
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

    private fun getAddressFromServer(latitude: Double, longitude: Double) {
        viewModelScope.launch {


        }
    }

    fun changeMovingState(movingState: Boolean) {
        _uiState.update { state ->
            state.copy(
                isMoving = movingState
            )
        }

        if (movingState) {
            _uiState.update { state ->
                state.copy(
                    isSelectBtnEnable = false
                )
            }
        } else if(uiState.value.isFromSearch){
            _uiState.update { state ->
                state.copy(
                    isSelectBtnEnable = true
                )
            }
        }
    }

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(DepartureMapEvent.NavigateToSearch)
        }
    }

    fun selectLocationFromSearch(
        latitude: Double,
        longitude: Double,
        landMark: String,
        address: String
    ) {
        Log.d(TAG,"selectLocation")
        _uiState.update { state ->
            state.copy(
                latitude = latitude,
                longitude = longitude,
                landMark = landMark,
                address = address,
                isFromSearch = true
            )
        }
    }

    fun selectDeparture() {
        viewModelScope.launch {
            _event.emit(
                DepartureMapEvent.SelectDeparture(
                    uiState.value.latitude,
                    uiState.value.longitude,
                    uiState.value.landMark,
                    uiState.value.address
                )
            )
        }
    }

    fun locationBtnClicked() {
        viewModelScope.launch {
            _event.emit(DepartureMapEvent.MoveToCurLocation)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(DepartureMapEvent.NavigateToBack)
        }
    }

    fun clear() {
        _uiState.update {
            DepartureMapUiState()
        }
    }
}