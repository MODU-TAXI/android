package com.motax.modutaxi.presentation.ui.main.partylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.partylist.mapper.toUiTaxiPotItem
import com.motax.modutaxi.presentation.ui.main.partylist.model.UiTaxiPotItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaxiPotListUiState(
    val taxiPotList: List<UiTaxiPotItem> = emptyList()
)

@HiltViewModel
class TaxiPotListViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaxiPotListUiState())
    val uiState: StateFlow<TaxiPotListUiState> = _uiState.asStateFlow()

    fun getTaxiPots() {
        viewModelScope.launch {
            repository.getTaxiPotList(0, 20).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        taxiPotList = it.result.map { data -> data.toUiTaxiPotItem(::navigateToMatchDetail) }
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun navigateToMatchDetail(id: Long) {
        viewModelScope.launch {
            repository.enterPot(id).onSuccess {

            }.onFailure {

            }
        }
    }

//    fun isCategorySelected(category: TaxiPotCategory): Boolean {
//        return selectedCategories.contains(category)
//    }
//
//    //카테고리 리스트에 만약 존재하면 지우고 아니라면 추가
//    fun toggleCategorySelection(category: TaxiPotCategory) {
//        if (selectedCategories.contains(category)) {
//            selectedCategories.remove(category)
//        } else {
//            selectedCategories.add(category)
//        }
//        filterTaxipots()
//    }
//
//    //현재 선택된 카테고리에 따라 택시팟 목록 필터링
//    private fun filterTaxipots() {
//        _taxipots.value = if (selectedCategories.isEmpty()) {
//            allUiTaxiPotItems
//        } else {
//            allUiTaxiPotItems.filter { taxipot ->
//                selectedCategories.all { taxipotCategory ->
//                    taxipot.categories.contains(taxipotCategory)
//                }
//            }
//        }
//    }

}