package com.motax.modutaxi.presentation.ui.main.mypage.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.account.model.UiAccountItem
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

data class AccountUiState(
    val accountList: List<UiAccountItem> = emptyList()
)

sealed class AccountEvent {
    data object NavigateToMyPage : AccountEvent()
}

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<AccountEvent>()
    val event: SharedFlow<AccountEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            val dummyAccounts = listOf(
                UiAccountItem(id = 1, bankName = Bank.getDisplayName("KAKAO").toString(), accountNumber = "3333081489543"),
                UiAccountItem(id = 2, bankName = Bank.getDisplayName("NH").toString(), accountNumber = "3012312312323")
            )
            _uiState.update {
                it.copy(accountList = dummyAccounts)
            }
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(AccountEvent.NavigateToMyPage)
        }
    }

    fun deleteAccount(account: UiAccountItem) {
        viewModelScope.launch {
            val updatedList = _uiState.value.accountList.toMutableList().apply {
                remove(account)
            }
            _uiState.value = _uiState.value.copy(accountList = updatedList)
        }
    }
}