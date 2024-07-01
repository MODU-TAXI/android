package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model.UsageHistoryUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class UsageHistoryUiState(
    val year: Int = 0,
    val month: Int = 0,
    val savedMoney: Int = 0,
    val beforeProgress: Int = 0,
    val beforeMoney: Int = 0,
    val afterProgress: Int = 0,
    val afterMoney: Int = 0,
    val monthlyUsageDetailList: List<UsageHistoryUiItem> = emptyList()
)

@HiltViewModel
class UsageHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageHistoryUiState())
    val uiState: StateFlow<UsageHistoryUiState> = _uiState.asStateFlow()

    init {
        setCurrentYearMonth()
        loadMonthlyUsageHistory()
    }

    private fun setCurrentYearMonth() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val monthFormat = SimpleDateFormat("M", Locale.getDefault())
        val currentDate = Date()
        val currentYear = dateFormat.format(currentDate).toInt()
        val currentMonth = monthFormat.format(currentDate).toInt()
        _uiState.update { it.copy(year = currentYear, month = currentMonth) }
    }

    private fun loadMonthlyUsageHistory() {
        viewModelScope.launch {
            val year = _uiState.value.year
            val month = _uiState.value.month
            mainRepository.getMonthlyUsageHistory(year, month)
                .onSuccess { response ->
                    val items = response.historyList.map { responseItem ->
                        UsageHistoryUiItem(
                            historyId = responseItem.historyId,
                            departureTime = responseItem.departureTime,
                            departureName = responseItem.departureName,
                            arrivalName = responseItem.arrivalName,
                            portionCharge = responseItem.portionCharge
                        )
                    }
                    _uiState.update {
                        it.copy(
                            savedMoney = response.accumulatePortionCharge,
                            beforeProgress = calculateProgress(response.totalCharge, response.accumulatePortionCharge),
                            beforeMoney = response.totalCharge,
                            afterProgress = calculateProgress(response.totalCharge, response.accumulatePortionCharge),
                            afterMoney = response.accumulatePortionCharge,
                            monthlyUsageDetailList = items
                        )
                    }
                }
                .onFailure {}
        }
    }

    private fun calculateProgress(total: Int, portion: Int): Int {
        return if (total == 0) 0 else (portion * 100) / total
    }
}
