package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UsageHistoryItem
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
    val monthlyUsageDetailList: List<UsageHistoryItem> = emptyList()
)

@HiltViewModel
class UsageHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageHistoryUiState())
    val uiState: StateFlow<UsageHistoryUiState> = _uiState.asStateFlow()

    init {
        loadDummyData()
        setCurrentYearMonth()
    }

    private fun setCurrentYearMonth() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val monthFormat = SimpleDateFormat("M", Locale.getDefault())
        val currentDate = Date()
        val currentYear = dateFormat.format(currentDate).toInt()
        val currentMonth = monthFormat.format(currentDate).toInt()
        _uiState.update { it.copy(year = currentYear, month = currentMonth) }
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            val dummyData = generateDummyData()
            _uiState.update {

                it.copy(
                    savedMoney = 51130,
                    beforeProgress = 90,
                    beforeMoney = 143000,
                    afterProgress = 50,
                    afterMoney = 73000,
                    monthlyUsageDetailList = dummyData
                )
            }
        }
    }

    private fun generateDummyData(): List<UsageHistoryItem> {
        return listOf(
            UsageHistoryItem(
                historyId = 1,
                departureTime = "2024-06-29T11:23:02.917Z",
                departureName = "센트리빌",
                arrivalName = "주안역",
                portionCharge = 2613
            ),
            UsageHistoryItem(
                historyId = 2,
                departureTime = "2024-06-28T14:45:30.123Z",
                departureName = "강남역",
                arrivalName = "잠실역",
                portionCharge = 3400
            ),
            UsageHistoryItem(
                historyId = 3,
                departureTime = "2024-06-27T09:15:20.456Z",
                departureName = "서울역",
                arrivalName = "인천공항",
                portionCharge = 55000
            )
        )
    }
}