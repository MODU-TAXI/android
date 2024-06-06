package com.motax.modutaxi.presentation.ui.main.showparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiTaxiPotMarkerItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotMarkerItem
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

data class ShowPartyUiState(
    val longitude: Double = 126.6538126,
    val latitude: Double = 37.4507292,
    val isMoving: Boolean = false,
    val isFromSearch: Boolean = false,
    val searchKeyword: String = "",
    val markerDataList: List<UiTaxiPotMarkerItem> = emptyList(),
    val selectedMarkerData: UiTaxiPotMarkerItem = UiTaxiPotMarkerItem(),
    val spotFilter: String = "",
    val roomTagFilter: List<String> = emptyList()
)

sealed class ShowPartyEvent {
    data object MoveToCurLocation : ShowPartyEvent()
    data class SetMarkers(val list: List<UiTaxiPotMarkerItem>) : ShowPartyEvent()
    data object NavigateToSearch : ShowPartyEvent()
    data object NavigateToCreateParty : ShowPartyEvent()
    data class NavigateToMatchDetail(val id: Long) : ShowPartyEvent()
}

@HiltViewModel
class ShowPartyViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<ShowPartyEvent>()
    val event: SharedFlow<ShowPartyEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ShowPartyUiState())
    val uiState: StateFlow<ShowPartyUiState> = _uiState.asStateFlow()

    private var spotFilterId: Long = 0

    fun getTaxiPotMarkers(latitude: Double = uiState.value.latitude, longitude: Double = uiState.value.longitude) {

        val filterMap = hashMapOf<String, Long>()

        if (uiState.value.spotFilter.isNotBlank()) {
            filterMap["spotId"] = spotFilterId
        }

        viewModelScope.launch {
            repository.getTaxiPotListRadius(
                filterMap,
                1500,
                latitude,
                longitude,
                uiState.value.roomTagFilter
            ).onSuccess {

                val newList = it.rooms.map { data -> data.toUiTaxiPotMarkerItem() }

                _event.emit(ShowPartyEvent.SetMarkers(newList.filter { data ->
                    data !in uiState.value.markerDataList
                }))

                _uiState.update { state ->
                    state.copy(
                        markerDataList = it.rooms.map { data -> data.toUiTaxiPotMarkerItem() },
                        latitude = latitude,
                        longitude = longitude
                    )
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
    }

    fun locationBtnClicked() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.MoveToCurLocation)
        }
    }

    fun selectLocationFromSearch(
        latitude: Double,
        longitude: Double,
        landMark: String,
    ) {
        _uiState.update { state ->
            state.copy(
                latitude = latitude,
                longitude = longitude,
                searchKeyword = landMark,
                isFromSearch = true
            )
        }
    }

    fun selectMarker(markerData: UiTaxiPotMarkerItem) {
        _uiState.update { state ->
            state.copy(
                selectedMarkerData = markerData,
            )
        }
    }

    fun navigateToCreateParty() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToCreateParty)
        }
    }

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToSearch)
        }
    }

    fun navigateToMatchDetail(id: Long) {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToMatchDetail(id))
        }
    }

}