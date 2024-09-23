package com.motax.modutaxi.presentation.ui.main.chat.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.util.Bank
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

data class PaymentUiState(
    val name: String = "",
    val chargeString: String = "",
    val charge: String = "",
    val bankName: String = "",
    val accountString: String = ""
)

sealed class PaymentEvent {
    data object NavigateBack : PaymentEvent()
    data class CopyClipBoard(val accountString: String) : PaymentEvent()
    data object MoveToToss : PaymentEvent()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PaymentEvent>()
    val event: SharedFlow<PaymentEvent> = _event.asSharedFlow()

    var totalCharge = 0

    val bankLogo = MutableStateFlow(0)

    fun getPaymentInfo() {
        viewModelScope.launch {
            repository.getPaymentInfo(CalculateForm.roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        name = it.ownerName,
                        accountString = it.accountNumber,
                        bankName = Bank.getDisplayName(it.bank),
                    )
                }

                totalCharge = it.totalCharge

                bankLogo.value = Bank.fromName(it.bank).logoResId

                getPaymentMember()
            }.onFailure {

            }
        }
    }

    private fun getPaymentMember() {
        viewModelScope.launch {
            repository.getPaymentMembersState(CalculateForm.roomId).onSuccess {
                var count = 0
                it.participantList.forEach { data ->
                    if (data.status != "DEACTIVATED") count++
                }

                _uiState.update { state ->
                    state.copy(
                        chargeString = (totalCharge / count).formatNumberWithCommas() + "원",
                        charge = (totalCharge / count).toString()
                    )
                }

            }.onFailure {

            }
        }
    }

    fun moveToToss(){
        viewModelScope.launch {
            _event.emit(PaymentEvent.MoveToToss)
        }
    }

    fun paymentComplete() {
        viewModelScope.launch {
            repository.paymentComplete(CalculateForm.roomId).onSuccess {
                _event.emit(PaymentEvent.NavigateBack)
            }.onFailure {

            }
        }
    }

    fun copyClipBoard() {
        viewModelScope.launch {
            _event.emit(PaymentEvent.CopyClipBoard(uiState.value.accountString))
        }
    }

}