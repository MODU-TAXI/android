package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.explanation

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

data class ExplanationUiState(
    val nickname: String = ""
)

sealed class ExplanationEvent {
    data object NavigateToReason : ExplanationEvent()
}

@HiltViewModel
class ExplanationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _event = MutableSharedFlow<ExplanationEvent>()
    val event: SharedFlow<ExplanationEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ExplanationUiState())
    val uiState: StateFlow<ExplanationUiState> = _uiState.asStateFlow()

    fun loadNickname() {
        viewModelScope.launch {

            val memberNickName = dataStoreManager.getMemberNickName()

            _uiState.update { state ->
                state.copy(
                    nickname = memberNickName.toString()
                )
            }
        }
    }

    fun navigateToReason() {
        viewModelScope.launch {
            _event.emit(ExplanationEvent.NavigateToReason)
        }
    }
}