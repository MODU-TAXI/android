package com.motax.modutaxi.presentation.ui.intro.signup.emailauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmailAuthorizationUiState(
    val btnState: EmailAuthBtnState = EmailAuthBtnState.Able,
    val time: String = "",
)

sealed class EmailAuthBtnState {
    data object Able : EmailAuthBtnState()
    data class Disable(val msg: String) : EmailAuthBtnState()
    data class AuthSuccess(val msg: String) : EmailAuthBtnState()
    data class AuthFailure(val msg: String) : EmailAuthBtnState()
}

sealed class EmailAuthEvent {
    data object NavigateToComplete: EmailAuthEvent()
}

@HiltViewModel
class OnboardingSchoolAuthorizationEnterCodeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(EmailAuthorizationUiState())
    val uiState: StateFlow<EmailAuthorizationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<EmailAuthEvent>()
    val event : SharedFlow<EmailAuthEvent> = _event.asSharedFlow()

    var curJob : Job?= null

    init {
        checkTimer()
    }

    private fun checkTimer() {
        curJob = viewModelScope.launch {
            var time = 180
            while(time != 0) {
                delay(1000)
                time--;
                _uiState.update { state ->
                    state.copy(
                        time = "${time/60}:" + if(time % 60 >= 10) "${time%60}" else "0${time % 60}"
                    )
                }
            }
            _uiState.update {state ->
                state.copy(
                    btnState = EmailAuthBtnState.Disable("인증번호 시간 만료."),
                )
            }
        }
    }

    fun checkAuthCode() {
        viewModelScope.launch {

            _uiState.update {state ->
                state.copy(
                    btnState = EmailAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다")
                )
            }

            _uiState.update {state ->
                state.copy(
                    btnState = EmailAuthBtnState.AuthSuccess("인증번호 검증 성공")
                )
            }

            _event.emit(EmailAuthEvent.NavigateToComplete)
        }
    }

    fun resendAuthCode() {
        viewModelScope.launch {

            // todo 인증번호 재전송

            _uiState.update { state ->
                state.copy(
                    btnState = EmailAuthBtnState.Able
                )
            }
            curJob?.cancel()
            checkTimer()
        }
    }
}