package com.motax.modutaxi.presentation.ui.main.chat.calculate.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.chat.mapper.toUiCalculateParticipant
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.ui.main.chat.model.UiCalculateParticipantItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CalculateConfirmUiState(
    val name: String = "",
    val calculateMembers: List<UiCalculateParticipantItem> = emptyList(),
    val nonCalculateMembers: List<UiCalculateParticipantItem> = emptyList(),
    val calculateMemebrCount: Int = 0,
    val totalAmount: Int = 0,
    val amountPerCount: Int = 0,
    val totalAmountString: String = "",
    val amountPerCountString: String = ""
)

sealed class CalculateConfirmEvent {
    data object CopyClipBoard : CalculateConfirmEvent()
    data object NavigateToCalculateComplete : CalculateConfirmEvent()
}

@HiltViewModel
class CalculateConfirmViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculateConfirmUiState())
    val uiState: StateFlow<CalculateConfirmUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CalculateConfirmEvent>()
    val events: SharedFlow<CalculateConfirmEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            authRepository.getMemberName()?.let {
                _uiState.update { state ->
                    state.copy(
                        name = it,
                        totalAmount = CalculateForm.totalCharge,
                        totalAmountString = CalculateForm.totalCharge.formatNumberWithCommas()
                    )
                }

                getParticipants()
            } ?: run {
            }
        }
    }

    private fun getParticipants() {
        viewModelScope.launch {
            val counts = 4
            _uiState.update { state ->
                state.copy(
                    calculateMembers = listOf(
                        UiCalculateParticipantItem(
                            1,
                            "",
                            "진성",
                            "5000",
                            true,
                            true,
                            ::changeParticipantState
                        ),
                        UiCalculateParticipantItem(
                            2,
                            "",
                            "하연",
                            "5000",
                            false,
                            true,
                            ::changeParticipantState
                        ),
                        UiCalculateParticipantItem(
                            3,
                            "",
                            "민우",
                            "5000",
                            false,
                            true,
                            ::changeParticipantState
                        ),
                        UiCalculateParticipantItem(
                            4,
                            "",
                            "진로",
                            "5000",
                            false,
                            true,
                            ::changeParticipantState
                        )
                    ),
                    amountPerCount = uiState.value.totalAmount / counts,
                    amountPerCountString = (uiState.value.totalAmount / counts).formatNumberWithCommas(),
                    calculateMemebrCount = counts
                )
            }

            repository.getTaxiPotParticipants(CalculateForm.roomId).onSuccess {
                val count = it.inList.size
                _uiState.update { state ->
                    state.copy(
                        calculateMembers = it.inList.map { data ->
                            data.toUiCalculateParticipant(::changeParticipantState)
                        },
                        amountPerCount = uiState.value.totalAmount / count,
                        amountPerCountString = (uiState.value.totalAmount / count).toInt()
                            .formatNumberWithCommas(),
                        calculateMemebrCount = count
                    )
                }


            }.onFailure {

            }
        }
    }

    private fun changeParticipantState(item: UiCalculateParticipantItem) {
        if (item.isCalculate) {
            if (uiState.value.calculateMemebrCount > 1) {
                val newCount = uiState.value.calculateMemebrCount - 1
                _uiState.update { state ->
                    state.copy(
                        calculateMembers = uiState.value.calculateMembers.filter {
                            it.memberId != item.memberId
                        },
                        nonCalculateMembers = uiState.value.nonCalculateMembers + item.copy(
                            isCalculate = false
                        ),
                        calculateMemebrCount = newCount,
                        amountPerCount = uiState.value.totalAmount / newCount,
                        amountPerCountString = (uiState.value.totalAmount / newCount).formatNumberWithCommas(),
                    )
                }
            } else {
                // 뺄수 없음 모달
            }

        } else {
            val newCount = uiState.value.calculateMemebrCount + 1
            _uiState.update { state ->
                state.copy(
                    nonCalculateMembers = uiState.value.nonCalculateMembers.filter {
                        it.memberId != item.memberId
                    },
                    calculateMemebrCount = newCount,
                    amountPerCount = uiState.value.totalAmount / newCount,
                    amountPerCountString = (uiState.value.totalAmount / newCount).formatNumberWithCommas(),
                    calculateMembers = uiState.value.calculateMembers + item.copy(isCalculate = true)
                )
            }
        }
    }

    fun copyClipBoard() {
        viewModelScope.launch {
            _events.emit(CalculateConfirmEvent.CopyClipBoard)
        }
    }

    fun requestCalculate() {
        viewModelScope.launch {
            repository.requestCalculate(
                CalculateForm.roomId,
                CalculateForm.accountId,
                CalculateForm.totalCharge,
                uiState.value.calculateMembers.map { it.memberId },
                uiState.value.nonCalculateMembers.map { it.memberId }
            ).onSuccess {
                _events.emit(CalculateConfirmEvent.NavigateToCalculateComplete)
            }.onFailure { }
        }
    }

}