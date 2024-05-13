package com.motax.modutaxi.presentation.ui.main.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.repository.NaverMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState(
    val address: String = ""
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val naverMapRepository: NaverMapRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun getAddressFromGeo(latitude: String, longitude: String){
        viewModelScope.launch {
            naverMapRepository.getAddressFromGeo(
                "coordsToaddr",
                "${longitude},${latitude}",
                "json",
                "roadaddr",
            ).onSuccess {

            }.onFailure {

            }
        }
    }
}