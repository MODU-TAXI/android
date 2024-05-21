package com.motax.modutaxi.presentation.ui.main.createparty.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.NaverRepository
import com.motax.modutaxi.presentation.ui.calculateDistance
import com.motax.modutaxi.presentation.ui.main.createparty.search.model.UiSearchResultItem
import com.motax.modutaxi.presentation.ui.toDistanceString
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

data class AddressSearchUiState(
    val searchResult: List<UiSearchResultItem> = emptyList(),
    val curLocation: Location = Location(126.6538126, 37.4507292)
)

sealed class AddressSearchEvent {
    data object NavigateToBack : AddressSearchEvent()
    data class SelectLocation(val latitude: Double, val longitude: Double, val landMark: String) :
        AddressSearchEvent()
}

data class Location(val x: Double, val y: Double)

@HiltViewModel
class AddressSearchViewModel @Inject constructor(
    private val repository: NaverRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<AddressSearchEvent>()
    val event: SharedFlow<AddressSearchEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AddressSearchUiState())
    val uiState: StateFlow<AddressSearchUiState> = _uiState.asStateFlow()

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
                curLocation = Location(longitude, latitude)
            )
        }
    }

    private fun getSearchResults(keyword: String) {
        viewModelScope.launch {

            repository.getSearchResultList(keyword, 5).onSuccess { searchResultData ->
                _uiState.update { state ->
                    state.copy(
                        searchResult = searchResultData.results.map { dataItem ->

                            val location = Location(
                                dataItem.mapx.toDouble() / 1e7,
                                dataItem.mapy.toDouble() / 1e7
                            )
                            val distance = calculateDistance(
                                uiState.value.curLocation.x,
                                uiState.value.curLocation.y,
                                location.x,
                                location.y
                            )

                            UiSearchResultItem(
                                dataItem.title,
                                dataItem.roadAddress,
                                distance.toDistanceString(),
                                keyword,
                                location.y,
                                location.x,
                                ::selectLocation
                            )
                        }
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun selectLocation(latitude: Double, longitude: Double, landMark: String) {
        viewModelScope.launch {
            _event.emit(AddressSearchEvent.SelectLocation(latitude, longitude, landMark))
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(AddressSearchEvent.NavigateToBack)
        }
    }
}