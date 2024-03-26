package com.motax.modutaxi.presentation.ui.intro.signup.phoneauth

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

data class PhoneAuthorizationUiState(
    val btnState: PhoneAuthBtnState = PhoneAuthBtnState.Able,
    val time: String = "",
)

sealed class PhoneAuthBtnState {
    data object Able : PhoneAuthBtnState()
    data class Disable(val msg: String) : PhoneAuthBtnState()
    data class AuthSuccess(val msg: String) : PhoneAuthBtnState()
    data class AuthFailure(val msg: String) : PhoneAuthBtnState()
}

sealed class PhoneAuthEvent{
    data object NavigateToQuestionHowToKnow: PhoneAuthEvent()
}

@HiltViewModel
class OnboardingPhoneAuthViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PhoneAuthorizationUiState())
    val uiState: StateFlow<PhoneAuthorizationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PhoneAuthEvent>()
    val event: SharedFlow<PhoneAuthEvent> = _event.asSharedFlow()

    val authorizationCode = MutableStateFlow("")

  var curJob : Job ?= null

    init {
        checkTimer()
    }

    private fun checkTimer() {
        curJob = viewModelScope.launch {
            var time = 180
            while (time != 0) {
                delay(1000)
                time--
                _uiState.update { state ->
                    state.copy(
                        time = "${time / 60}:" + if (time % 60 >= 10) "${time % 60}" else "0${time % 60}"
                    )
                }
            }
            _uiState.update { state ->
                state.copy(
                    btnState = PhoneAuthBtnState.Disable("인증번호 시간 만료."),
                )
            }
        }
    }

    fun checkAuthCode() {

        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(
                    btnState = PhoneAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다")
                )
            }

            _uiState.update { state ->
                state.copy(
                    btnState = PhoneAuthBtnState.AuthSuccess("인증번호 검증 성공")
                )
            }

            _event.emit(PhoneAuthEvent.NavigateToQuestionHowToKnow)
        }
    }

    fun resendAuthCode() {
        viewModelScope.launch {

            // todo 인증번호 재전송

            _uiState.update { state ->
                state.copy(
                    btnState = PhoneAuthBtnState.Able
                )
            }
            curJob?.cancel()
            checkTimer()
        }
    }
}