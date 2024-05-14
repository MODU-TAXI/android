package com.motax.modutaxi.presentation.ui.main.createparty

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class CreatePartyUiState(
    val departureLongitude : Double = 0.0,
    val departureLatitude: Double = 0.0,
    val spotId: Long = 0,
    val departureName: String = "",
    val arrivalName: String = "",
    val wishHeadCount: WishHeadCount = WishHeadCount.EMPTY,
)

sealed class CreatePartyEvent{

}

@HiltViewModel
class CreatePartyViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(CreatePartyUiState())
    val uiState : StateFlow<CreatePartyUiState> = _uiState.asStateFlow()


    fun setDepartureInfo(
        longitude: Double,
        latitude: Double,
        name: String
    ){
        _uiState.update { state ->
            state.copy(
                departureLatitude = latitude,
                departureLongitude = longitude,
                departureName = name
            )
        }
    }

    fun selectWishHeadCount(count : WishHeadCount){
        _uiState.update { state ->
            state.copy(
                wishHeadCount = count
            )
        }
    }

}

enum class WishHeadCount(val count: Int){
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3)
}