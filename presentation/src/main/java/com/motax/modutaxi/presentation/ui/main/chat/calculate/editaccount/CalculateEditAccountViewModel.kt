package com.motax.modutaxi.presentation.ui.main.chat.calculate.editaccount

import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.ui.main.chat.model.UiAccountItem
import com.motax.modutaxi.presentation.util.Bank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CalculateEditAccountUiState(
    val registeredBank: List<UiAccountItem> = emptyList()
)

@HiltViewModel
class CalculateEditAccountViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CalculateEditAccountUiState())
    val uiState: StateFlow<CalculateEditAccountUiState> = _uiState.asStateFlow()

    val selectedBank = MutableStateFlow(Bank.EMPTY)

    fun selectBank(bank: Bank) {
        selectedBank.update {
            bank
        }
    }

}