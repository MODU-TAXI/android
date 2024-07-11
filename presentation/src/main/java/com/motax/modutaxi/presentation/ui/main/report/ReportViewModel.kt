package com.motax.modutaxi.presentation.ui.main.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.shortenAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val isReportContentNotBlank: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val targetId: Long = -1L,
    val roomId: Long = -1L,
    val nickName: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val departureTime: String = "",
    val expectedChargePerPerson: String = "",
    val content: String = "",
    val reportType: String = ""
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun fetchMemberProfile(memberId: Long) {
        viewModelScope.launch {
            val result = mainRepository.getMemberProfile(memberId)
            result.onSuccess { profile ->
                _uiState.update {
                    it.copy(
                        nickName = profile.nickname
                    )
                }
            }
        }
    }

    fun fetchTaxiPotPreview(roomId: Long) {
        viewModelScope.launch {
            val result = mainRepository.getTaxiPotPreview(roomId)
            result.onSuccess { preview ->
                _uiState.update {
                    it.copy(
                        departureName = preview.departureName.shortenAddress(),
                        arrivalName = preview.arrivalName,
                        departureTime = preview.departureTime,
                        expectedChargePerPerson = preview.expectedChargePerPerson.formatNumberWithCommas()
                    )
                }
            }.onFailure { }
        }
    }

    //임시 연결
    fun setTargetAndRoomIds(targetId: Long, roomId: Long) {
        _uiState.value = _uiState.value.copy(
            targetId = 7,
            roomId = 55
        )
    }

    fun updateReportType(selectedItem: String) {
        val reportType = mapReportType(selectedItem)
        _uiState.update {
            it.copy(reportType = reportType)
        }
        updateButtonState()
    }

    fun updateReportContent(content: String) {
        _uiState.update {
            it.copy(
                isReportContentNotBlank = content.isNotBlank(),
                content = content
            )
        }
        updateButtonState()
    }


    private fun updateButtonState() {
        val isButtonEnabled =
            _uiState.value.reportType.isNotEmpty() && _uiState.value.isReportContentNotBlank
        _uiState.value = _uiState.value.copy(isButtonEnabled = isButtonEnabled)
    }

    fun postReport() {
        viewModelScope.launch {
            val result = mainRepository.postReport(
                _uiState.value.roomId,
                _uiState.value.targetId,
                _uiState.value.reportType,
                _uiState.value.content
            )
            result.onSuccess {
                Log.d("debugging", "신고 성공!!!")
            }.onFailure {
            }
        }
    }

    private fun mapReportType(selectedItem: String): String {
        return when (selectedItem) {
            "중간에 채팅방을 나갔어요" -> "LEAVE_CHATROOM"
            "제 시간에 도착하지 않았어요" -> "LATE"
            "먼저 출발했어요" -> "FIRST_GONE"
            "연락이 되지 않아요" -> "OUT_OF_TOUCH"
            "정산 금액이 예상과 달라요" -> "UNEXPECTED_ACCOUNTS"
            "정산 금액을 보내주지 않았어요" -> "NON_REMIT"
            "기타 (직접 입력할게요)" -> "ETC"
            else -> "ETC"
        }
    }
}