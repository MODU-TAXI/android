package com.motax.modutaxi.presentation.ui.main.createparty.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.NaverRepository
import com.motax.modutaxi.presentation.ui.calculateDistance
import com.motax.modutaxi.presentation.ui.main.createparty.mapper.toUiSearchResultItem
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiSearchResultItem
import com.motax.modutaxi.presentation.ui.toDistanceString
import com.motax.modutaxi.presentation.util.Constants.TAG
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
    val curLatitude: Double = 37.4507292,
    val curLongitude: Double = 126.6538126
)

sealed class AddressSearchEvent {
    data object NavigateToBack : AddressSearchEvent()
    data class SelectLocation(val latitude: Double, val longitude: Double, val landMark: String) :
        AddressSearchEvent()
}

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