package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.MainRepository
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

data class ConfirmUiState(
    val nickname: String = ""
)

sealed class ConfirmEvent {
    data object NavigateToComplete : ConfirmEvent()
}

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<ConfirmEvent>()
    val event: SharedFlow<ConfirmEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ConfirmUiState())
    val uiState: StateFlow<ConfirmUiState> = _uiState.asStateFlow()

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

    fun deleteMember() {
        viewModelScope.launch {
            mainRepository.deleteMember().onSuccess {
                dataStoreManager.clearUserData()
                _event.emit(ConfirmEvent.NavigateToComplete)
            }.onFailure { }
        }
    }
}