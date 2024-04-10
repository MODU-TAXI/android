package com.motax.modutaxi.presentation.ui.taxipotlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.adapters.Category
import com.motax.modutaxi.presentation.adapters.Taxipot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxipotListViewModel @Inject constructor() : ViewModel() {
    private val _taxipots = MutableLiveData<List<Taxipot>>()
    val taxipotList: LiveData<List<Taxipot>> = _taxipots

    private var allTaxipots: List<Taxipot> = emptyList()

    init {
        loadTaxipots()
    }

    fun loadTaxipots() {
        allTaxipots = listOf(
            Taxipot("1", "Taxipot 1", 2, 3, 12300, 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역", listOf(Category.STUDENT_VERIFICATION, Category.FEMALES_ONLY, Category.QUIET)),
            Taxipot("2", "Taxipot 2", 2, 3, 12300, 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역",listOf(Category.STUDENT_VERIFICATION, Category.FEMALES_ONLY)),
            Taxipot("3", "Taxipot 3", 2, 3, 12300 , 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역", listOf(Category.DEADLINE, Category.STUDENT_VERIFICATION, Category.FEMALES_ONLY, Category.QUIET)),
            Taxipot("4", "Taxipot 4", 2, 3, 12300, 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역", listOf(Category.QUIET)),
            Taxipot("5", "Taxipot 4", 2, 3, 12300, 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역", listOf(Category.QUIET)),
            Taxipot("6", "Taxipot 4", 2, 3, 12300, 3, "3월 25일 (월) 14:45 출발", "인하대학교 후문 -> 주안역", listOf(Category.QUIET)),
        )
        _taxipots.value = allTaxipots
    }

    fun filterDeadlineTaxipots() {
        _taxipots.value = allTaxipots.filter { it.categories.contains(Category.DEADLINE)}
    }
}