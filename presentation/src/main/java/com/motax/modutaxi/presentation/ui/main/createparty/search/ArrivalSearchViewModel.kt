package com.motax.modutaxi.presentation.ui.main.createparty.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.domain.repository.NaverRepository
import com.motax.modutaxi.presentation.ui.main.createparty.mapper.toUiArrivalSpotItem
import com.motax.modutaxi.presentation.ui.main.createparty.mapper.toUiSearchResultItem
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiArrivalSpotItem
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiSearchResultItem
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiSpotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiSpotListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArrivalSearchUiState(
    val searchResult: List<UiSearchResultItem> = emptyList(),
    val curLatitude: Double = 37.4507292,
    val curLongitude: Double = 126.6538126,
    val nearSpot: String = "",
    val nearSpotLatitude: Double = 0.0,
    val nearSpotLongitude: Double = 0.0,
    val spotList: List<UiArrivalSpotItem> = emptyList()
)

sealed class ArrivalSearchEvent {
    data object NavigateToBack : ArrivalSearchEvent()
    data class SelectLocation(
        val latitude: Double,
        val longitude: Double,
        val landMark: String,
        val address: String,
        val isSpot: Boolean
    ) : ArrivalSearchEvent()
}

@HiltViewModel
class ArrivalSearchViewModel @Inject constructor(
    private val repository: NaverRepository,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<ArrivalSearchEvent>()
    val event: SharedFlow<ArrivalSearchEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ArrivalSearchUiState())
    val uiState: StateFlow<ArrivalSearchUiState> = _uiState.asStateFlow()

    val keyword = MutableStateFlow("")

    init {
        keyword.onEach { newKeyword ->
            if (newKeyword.isNotBlank()) {
                getSearchResults(newKeyword)
            } else {
                _uiState.update { state ->
                    state.copy(searchResult = emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setCurLocation(latitude: Double, longitude: Double) {
        _uiState.update { state ->
            state.copy(
                curLongitude = longitude,
                curLatitude = latitude
            )
        }
    }

    private fun getSearchResults(keyword: String) {
        viewModelScope.launch {

            repository.getSearchResultList(keyword, 5).onSuccess { searchResultData ->

                _uiState.update { state ->
                    state.copy(
                        searchResult = searchResultData.results.map { dataItem ->
                            dataItem.toUiSearchResultItem(
                                uiState.value.curLatitude,
                                uiState.value.curLongitude,
                                keyword,
                                ::selectLocation
                            )
                        }.sortedBy { it.distance }
                    )
                }

                if(uiState.value.searchResult.isNotEmpty()){
                    val firstResult = uiState.value.searchResult[0]
                    getNearSpot(firstResult.longitude, firstResult.latitude)
                } else {
                    _uiState.update { state ->
                        state.copy(
                            nearSpot = ""
                        )
                    }
                }
            }.onFailure {

            }
        }
    }

    private fun getNearSpot(searchLongitude: Double, searchLatitude: Double){
        viewModelScope.launch {
            mainRepository.getSpotList(0,1,searchLongitude,searchLatitude).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        nearSpot = it.spots[0].name,
                        nearSpotLatitude = it.spots[0].latitude,
                        nearSpotLongitude = it.spots[0].longitude
                    )
                }
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        nearSpot = ""
                    )
                }
            }
        }
    }

    fun getAllSpot(){
        viewModelScope.launch {
            mainRepository.getRadiusSpot(1000, 126.68045, 37.46504).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        spotList = it.spots.map{ data ->
                            data.toUiArrivalSpotItem { id, name, longitude, latitude ->
                                _uiState.update { state ->
                                    state.copy(
                                        nearSpotLongitude = longitude,
                                        nearSpotLatitude = latitude,
                                        nearSpot = name
                                    )
                                }

                                selectSpot()
                            }
                        }
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun selectLocation(
        latitude: Double,
        longitude: Double,
        landMark: String,
        address: String
    ) {
        viewModelScope.launch {
            _event.emit(ArrivalSearchEvent.SelectLocation(latitude, longitude, landMark, address, false))
        }
    }

    fun selectSpot(){
        viewModelScope.launch {
            _event.emit(ArrivalSearchEvent.SelectLocation(uiState.value.nearSpotLatitude, uiState.value.nearSpotLongitude, uiState.value.nearSpot, "", true))
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(ArrivalSearchEvent.NavigateToBack)
        }
    }

}