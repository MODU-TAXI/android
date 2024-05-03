package com.motax.modutaxi.presentation.ui.taxipotlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.adapters.TaxipotCategory
import com.motax.modutaxi.presentation.adapters.Taxipot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxipotListViewModel @Inject constructor() : ViewModel() {
    private val _taxipots = MutableLiveData<List<Taxipot>>()
    val taxipotList: LiveData<List<Taxipot>> = _taxipots

    private var allTaxipots: List<Taxipot> = emptyList()
    private val selectedCategories = mutableSetOf<TaxipotCategory>()

    init {
        loadTaxipots()
    }

    fun loadTaxipots() {
        allTaxipots = listOf(
            Taxipot(
                "1",
                "Taxipot 1",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(
                    TaxipotCategory.STUDENT_VERIFICATION,
                    TaxipotCategory.FEMALES_ONLY,
                    TaxipotCategory.QUIET
                )
            ),
            Taxipot(
                "2",
                "Taxipot 2",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxipotCategory.STUDENT_VERIFICATION, TaxipotCategory.FEMALES_ONLY)
            ),
            Taxipot(
                "3",
                "Taxipot 3",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(
                    TaxipotCategory.DEADLINE,
                    TaxipotCategory.STUDENT_VERIFICATION,
                    TaxipotCategory.FEMALES_ONLY,
                    TaxipotCategory.QUIET
                )
            ),
            Taxipot(
                "4",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxipotCategory.QUIET)
            ),
            Taxipot(
                "5",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxipotCategory.QUIET)
            ),
            Taxipot(
                "6",
                "Taxipot 4",
                2,
                3,
                12300,
                3,
                "3월 25일 (월) 14:45 출발",
                "인하대학교 후문 -> 주안역",
                listOf(TaxipotCategory.QUIET)
            ),
        )
        filterTaxipots()
    }

    fun isCategorySelected(category: TaxipotCategory): Boolean {
        return selectedCategories.contains(category)
    }

    //카테고리 리스트에 만약 존재하면 지우고 아니라면 추가
    fun toggleCategorySelection(category: TaxipotCategory) {
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
            allTaxipots
        } else {
            allTaxipots.filter { taxipot ->
                selectedCategories.all {taxipotCategory ->
                    taxipot.categories.contains(taxipotCategory)
                }
            }
        }
    }

}