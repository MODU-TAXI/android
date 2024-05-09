package com.motax.modutaxi.presentation.ui.main.taxipotsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.model.UiSearchResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaxiPotSearchResultUiState(
    val searchResult: List<UiSearchResultItem> = emptyList(),
    val focusedField: FocusedField = FocusedField.NONE

)

@HiltViewModel
class TaxipotSearchViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaxiPotSearchResultUiState())
    val uiState: StateFlow<TaxiPotSearchResultUiState> = _uiState.asStateFlow()

    fun getSearchResults() {
        TODO("repository에서 호출")
    }

    fun loadDummyData() {
        viewModelScope.launch {
            val dummyResults = listOf(
                UiSearchResultItem("주안역", "인천 미추홀구 주안로 41번길", "500m"),
                UiSearchResultItem("주안역 센트리빌", "인천 미추홀구 주안로 41번길", "500m"),
                UiSearchResultItem("주안역 3동 성모마리아 성당", "인천 미추홀구 경남서로 23-4", "3.0km")
            )

            _uiState.value = TaxiPotSearchResultUiState(searchResult = dummyResults)
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