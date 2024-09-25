package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model.UsageHistoryUiItem
import com.motax.modutaxi.presentation.ui.shortenAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    val savedMoney: String = "",
    val beforeProgress: Int = 0,
    val beforeMoney: String = "",
    val afterProgress: Int = 0,
    val afterMoney: String = "",
    val monthlyUsageDetailList: List<UsageHistoryUiItem> = emptyList()
)

sealed class UsageHistoryEvent {
    data class NavigateToUsageDetail(val id: Long) : UsageHistoryEvent()
    data object NavigateToMyPage : UsageHistoryEvent()
    data object ShowMonthPicker : UsageHistoryEvent()
}

@HiltViewModel
class UsageHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<UsageHistoryEvent>()
    val event: SharedFlow<UsageHistoryEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(UsageHistoryUiState())
    val uiState: StateFlow<UsageHistoryUiState> = _uiState.asStateFlow()

    init {
        setCurrentYearMonth()
        loadMonthlyUsageHistory()
    }

    fun showMonthPicker() {
        viewModelScope.launch {
            _event.emit(UsageHistoryEvent.ShowMonthPicker)
        }
    }
    fun updateYearMonth(year: Int, month: Int) {
        _uiState.update {
            it.copy(year = year, month = month)
        }
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

                        val dateTime = responseItem.departureTime
                        val datePart = dateTime.substringBefore("T").replace("-", ".")
                        val timePart = dateTime.substringAfter("T").substring(0, 5)

                        UsageHistoryUiItem(
                            historyId = responseItem.historyId,
                            departureTime = "$datePart $timePart",
                            departureName = responseItem.departureName.shortenAddress(),
                            arrivalName = responseItem.arrivalName,
                            portionCharge = responseItem.portionCharge.formatNumberWithCommas()
                        ) { id -> navigateToUsageDetail(id) }
                    }
                    _uiState.update {
                        it.copy(
                            savedMoney = response.accumulatePortionCharge.formatNumberWithCommas(),
                            beforeProgress = calculateProgress(response.totalCharge, response.accumulatePortionCharge),
                            beforeMoney = response.totalCharge.formatNumberWithCommas(),
                            afterProgress = calculateProgress(response.totalCharge, response.accumulatePortionCharge),
                            afterMoney = response.accumulatePortionCharge.formatNumberWithCommas(),
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

    private fun navigateToUsageDetail(id: Long) {
        viewModelScope.launch {
            _event.emit(UsageHistoryEvent.NavigateToUsageDetail(id))
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(UsageHistoryEvent.NavigateToMyPage)
        }
    }
}
