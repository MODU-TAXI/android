package com.motax.modutaxi.presentation.ui.main.chat.calculate.editaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.chat.mapper.toUiAccountItem
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.ui.main.chat.model.UiAccountItem
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

data class CalculateEditAccountUiState(
    val registeredBank: List<UiAccountItem> = emptyList()
)

sealed class CalculateEditAccountEvents {
    data object NavigateToConfirm : CalculateEditAccountEvents()
}

@HiltViewModel
class CalculateEditAccountViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculateEditAccountUiState())
    val uiState: StateFlow<CalculateEditAccountUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<CalculateEditAccountEvents>()
    val event: SharedFlow<CalculateEditAccountEvents> = _event.asSharedFlow()

    val selectedBank = MutableStateFlow(Bank.EMPTY)

    fun selectBank(bank: Bank) {
        selectedBank.update {
            bank
        }
    }

    fun getRegisteredBank() {
        viewModelScope.launch {
            repository.getAccounts().onSuccess {
                _uiState.update { state ->
                    state.copy(
                        registeredBank = it.accounts.map { data ->
                            data.toUiAccountItem()
                        }
                    )
                }
            }.onFailure {

            }
        }
    }

    fun navigateToConfirm() {
        viewModelScope.launch {
            CalculateForm.bank = selectedBank.value
            _event.emit(CalculateEditAccountEvents.NavigateToConfirm)
        }
    }

}