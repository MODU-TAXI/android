package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.reason

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
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

data class ReasonUiState(
    val nickname: String = "",
    val selectedReason: String = "",
    val showEtcReason: Boolean = false
)

sealed class ReasonEvent {
    data object NavigateToConfirm : ReasonEvent()
}

@HiltViewModel
class ReasonViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _event = MutableSharedFlow<ReasonEvent>()
    val event: SharedFlow<ReasonEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ReasonUiState())
    val uiState: StateFlow<ReasonUiState> = _uiState.asStateFlow()

    fun onReasonSelected(reason: String) {
        _uiState.update { state ->
            state.copy(
                selectedReason = reason,
                showEtcReason = reason == "기타"
            )
        }
    }

    fun navigateToConfirm() {
        viewModelScope.launch {
            _event.emit(ReasonEvent.NavigateToConfirm)
        }
    }

    fun loadMemberData() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    nickname = dataStoreManager.getMemberNickName().toString()
                )
            }
        }
    }
}