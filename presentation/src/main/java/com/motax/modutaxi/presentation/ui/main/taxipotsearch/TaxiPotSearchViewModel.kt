package com.motax.modutaxi.presentation.ui.main.taxipotsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.NaverRepository
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.model.UiSearchResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaxiPotSearchResultUiState(
    val searchResult: List<UiSearchResultItem> = emptyList(),
    val focusedField: FocusedField = FocusedField.NONE

)

@HiltViewModel
class TaxiPotSearchViewModel @Inject constructor(
    private val repository: NaverRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaxiPotSearchResultUiState())
    val uiState: StateFlow<TaxiPotSearchResultUiState> = _uiState.asStateFlow()

    val keyword = MutableStateFlow("")

    init {
        keyword.onEach { newKeyword ->
            if (newKeyword.isNotBlank()) {
                getSearchResults(newKeyword)
            }
        }.launchIn(viewModelScope)
    }

    fun updateKeyword(newKeyword: String) {
        keyword.value = newKeyword
    }

     private fun getSearchResults(keyword: String) {
         viewModelScope.launch {
             repository.getSearchResultList(keyword, 5).onSuccess { searchResultData ->
                 _uiState.update { state ->
                     state.copy(
                         searchResult = searchResultData.results.map { dataItem ->
                             UiSearchResultItem(dataItem.title, dataItem.roadAddress, "500m", keyword)
                         }
                     )
                 }
             }.onFailure {

             }
         }
     }

    fun focusNone() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.NONE
            )
        }
    }

    fun focusOnSearch() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.Search
            )
        }
    }
}

enum class FocusedField {
    NONE, Search
}