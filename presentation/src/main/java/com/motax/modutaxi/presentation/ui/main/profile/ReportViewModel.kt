package com.motax.modutaxi.presentation.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.matchdetail.MatchDetailEvent
import com.motax.modutaxi.presentation.ui.shortenAddress
import com.motax.modutaxi.presentation.util.extractMessageFromErrorBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val nickName: String = "",
    val departureName: String = "",
    val arrivalName: String = "",
    val departureTime: String = "",
    val expectedChargePerPerson: String = "",
)

sealed class ReportEvent {
    data object NavigateToBack : ReportEvent()
    data class ShowToastMessage(val msg: String) : ReportEvent()
    data object ShowLoading: ReportEvent()
    data object DismissLoading: ReportEvent()
}

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ReportEvent>()
    val event: SharedFlow<ReportEvent> = _event.asSharedFlow()

    private var targetId: Long = 0
    private var roomId: Long = 0

    val content = MutableStateFlow("")
    val reportType = MutableStateFlow("")

    val isDataReady =
        combine(content, reportType) { content, reportType ->
            content.isNotBlank() && reportType.isNotBlank()
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(), false
        )

    fun setTargetAndRoomIds(tId: Long, rId: Long) {
        targetId = tId
        roomId = rId

        fetchMemberProfile(targetId)
        fetchTaxiPotPreview(roomId)
    }

    private fun fetchMemberProfile(memberId: Long) {
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

    private fun fetchTaxiPotPreview(roomId: Long) {
        viewModelScope.launch {
            val result = mainRepository.getUsageDetail(roomId)
            result.onSuccess { preview ->
                _uiState.update {
                    it.copy(
                        departureName = preview.departureName.shortenAddress(),
                        arrivalName = preview.arrivalName,
                        departureTime = preview.departureTime,
                        expectedChargePerPerson = preview.portionCharge.formatNumberWithCommas()
                    )
                }
            }.onFailure { }
        }
    }


    fun updateReportType(selectedItem: String) {
        reportType.update {
            mapReportType(selectedItem)
        }
    }

    fun postReport() {
        viewModelScope.launch {
            _event.emit(ReportEvent.ShowLoading)
            mainRepository.postReport(
                roomId,
                targetId,
                reportType.value,
                content.value
            ).onSuccess {
                _event.emit(ReportEvent.ShowToastMessage("신고가 접수되었습니다"))
                _event.emit(ReportEvent.NavigateToBack)
            }.onFailure {
                when (it) {
                    is retrofit2.HttpException -> {
                        val message =
                            extractMessageFromErrorBody(it.response()?.errorBody()?.string())
                        _event.emit(ReportEvent.ShowToastMessage(message))
                    }
                }
                _event.emit(ReportEvent.DismissLoading)
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

    fun navigateBack(){
        viewModelScope.launch {
            _event.emit(ReportEvent.NavigateToBack)
        }
    }
}