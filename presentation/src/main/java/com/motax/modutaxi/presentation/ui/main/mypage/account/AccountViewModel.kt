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
    val accountList: List<UiAccountItem> = emptyList(),
    val isEmpty: Boolean = false
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

    fun loadAccounts() {
        viewModelScope.launch {
            repository.getAccounts()
                .onSuccess {response ->
                    val uiAccounts = response.accounts.map {
                        UiAccountItem(
                            id = it.id,
                            accountNumber = it.accountNumber,
                            bankName = Bank.getDisplayName(it.bank)
                        )
                    }
                    _uiState.update {
                        it.copy(
                            accountList = uiAccounts,
                            isEmpty = uiAccounts.isEmpty()
                        )
                    }
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

            val result = repository.deleteAccounts(account.id)

            if(result.isSuccess) {
                val updateList = _uiState.value.accountList.toMutableList().apply {
                    remove(account)
                }
                _uiState.update {
                    it.copy(
                        accountList = updateList,
                        isEmpty = updateList.isEmpty()
                    )
                }

            }
        }
    }


}