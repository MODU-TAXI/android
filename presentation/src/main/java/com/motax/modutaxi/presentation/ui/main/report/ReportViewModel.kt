package com.motax.modutaxi.presentation.ui.main.report

import androidx.lifecycle.ViewModel
import com.motax.modutaxi.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ReportUiState(
    val isReportTypeSelected: Boolean = false
)


@HiltViewModel
class ReportViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun selectReportType(selected: Boolean) {
        _uiState.value = _uiState.value.copy(isReportTypeSelected = selected)
    }
}