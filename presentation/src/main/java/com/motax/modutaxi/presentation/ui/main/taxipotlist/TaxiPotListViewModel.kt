package com.motax.modutaxi.presentation.ui.main.taxipotlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.UiTaxiPotItem
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.TaxiPotCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxiPotListViewModel @Inject constructor() : ViewModel() {
    private val _taxipots = MutableLiveData<List<UiTaxiPotItem>>()
    val uiTaxiPotItemList: LiveData<List<UiTaxiPotItem>> = _taxipots

    private var allUiTaxiPotItems: List<UiTaxiPotItem> = emptyList()
    private val selectedCategories = mutableSetOf<TaxiPotCategory>()

    init {
        loadTaxipots()
    }

    fun loadTaxipots() {
        allUiTaxiPotItems = listOf(
            UiTaxiPotItem(
                "1",
                "Taxipot 1",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(
                    TaxiPotCategory.STUDENT_VERIFICATION,
                    TaxiPotCategory.FEMALES_ONLY,
                    TaxiPotCategory.QUIET
                )
            ),
            UiTaxiPotItem(
                "2",
                "Taxipot 2",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxiPotCategory.STUDENT_VERIFICATION, TaxiPotCategory.FEMALES_ONLY)
            ),
            UiTaxiPotItem(
                "3",
                "Taxipot 3",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(
                    TaxiPotCategory.DEADLINE,
                    TaxiPotCategory.STUDENT_VERIFICATION,
                    TaxiPotCategory.FEMALES_ONLY,
                    TaxiPotCategory.QUIET
                )
            ),
            UiTaxiPotItem(
                "4",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxiPotCategory.QUIET)
            ),
            UiTaxiPotItem(
                "5",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxiPotCategory.QUIET)
            ),
            UiTaxiPotItem(
                "6",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxiPotCategory.QUIET)
            ),
        )
        filterTaxipots()
    }

    fun isCategorySelected(category: TaxiPotCategory): Boolean {
        return selectedCategories.contains(category)
    }

    //카테고리 리스트에 만약 존재하면 지우고 아니라면 추가
    fun toggleCategorySelection(category: TaxiPotCategory) {
        if (selectedCategories.contains(category)) {
            selectedCategories.remove(category)
        } else {
            selectedCategories.add(category)
        }
        filterTaxipots()
    }

    //현재 선택된 카테고리에 따라 택시팟 목록 필터링
    private fun filterTaxipots() {
        _taxipots.value = if (selectedCategories.isEmpty()) {
            allUiTaxiPotItems
        } else {
            allUiTaxiPotItems.filter { taxipot ->
                selectedCategories.all { taxipotCategory ->
                    taxipot.categories.contains(taxipotCategory)
                }
            }
        }
    }

}