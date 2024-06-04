package com.motax.modutaxi.presentation.ui.main.showparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val markerDataList: List<UiTaxiPotMarkerItem> = emptyList(),
    val selectedMarkerData: UiTaxiPotMarkerItem = UiTaxiPotMarkerItem()
)

sealed class ShowPartyEvent {
    data object MoveToCurLocation : ShowPartyEvent()
    data object SetMarkers : ShowPartyEvent()
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

}