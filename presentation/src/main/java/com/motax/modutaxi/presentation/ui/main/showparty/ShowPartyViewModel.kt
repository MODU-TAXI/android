package com.motax.modutaxi.presentation.ui.main.showparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotMarkerItem
import com.motax.modutaxi.presentation.ui.main.showparty.search.ShowPartySearchEvent
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
    val selectedMarkerData: UiTaxiPotMarkerItem = UiTaxiPotMarkerItem()
)

sealed class ShowPartyEvent {
    data object MoveToCurLocation : ShowPartyEvent()
    data object SetMarkers : ShowPartyEvent()
    data object NavigateToSearch: ShowPartyEvent()
    data object NavigateToCreateParty: ShowPartyEvent()
    data class NavigateToMatchDetail(val id: Long): ShowPartyEvent()
}

@HiltViewModel
class ShowPartyViewModel @Inject constructor(

) : ViewModel() {

    private val _event = MutableSharedFlow<ShowPartyEvent>()
    val event: SharedFlow<ShowPartyEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ShowPartyUiState())
    val uiState: StateFlow<ShowPartyUiState> = _uiState.asStateFlow()


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

    fun navigateToCreateParty(){
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToCreateParty)
        }
    }

    fun navigateToSearch(){
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToSearch)
        }
    }

    fun navigateToMatchDetail(id: Long){
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToMatchDetail(id))
        }
    }

}