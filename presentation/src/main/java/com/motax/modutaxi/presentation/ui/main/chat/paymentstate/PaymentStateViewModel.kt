package com.motax.modutaxi.presentation.ui.main.chat.paymentstate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.chat.mapper.toUiPaymentParticipant
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.ui.main.chat.model.UiPaymentParticipantItem
import com.motax.modutaxi.presentation.util.Bank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentStateUiState(
    val accountOwner: String = "",
    val totalCharge: String = "",
    val charge: String = "",
    val bankName: String = "",
    val accountString: String = "",
    val paymentParticipants: List<UiPaymentParticipantItem> = emptyList()
)

@HiltViewModel
class PaymentStateViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentStateUiState())
    val uiState: StateFlow<PaymentStateUiState> = _uiState.asStateFlow()

    var totalCharge = 0

    val bankLogo = MutableStateFlow(0)

    fun getPaymentInfo() {
        viewModelScope.launch {
            repository.getPaymentInfo(CalculateForm.roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        accountOwner = it.ownerName,
                        totalCharge = it.totalCharge.formatNumberWithCommas(),
                        bankName = Bank.getDisplayName(it.bank),
                        accountString = it.accountNumber
                    )
                }

                bankLogo.value = Bank.fromName(it.bank).logoResId

                getPaymentMember()

            }.onFailure { }
        }
    }

    private fun getPaymentMember() {
        viewModelScope.launch {
            repository.getPaymentMembersState(CalculateForm.roomId).onSuccess {
                var count = 0
                _uiState.update { state ->
                    state.copy(
                        paymentParticipants = it.participantList.map { data ->
                            if (data.status != "DEACTIVATED") {
                                count++
                                data.toUiPaymentParticipant()
                            } else {
                                data.toUiPaymentParticipant()
                            }
                        }
                    )
                }


                _uiState.update { state ->
                    state.copy(
                        charge = (totalCharge / count).formatNumberWithCommas() + "원"
                    )
                }

            }.onFailure {

            }
        }
    }

}