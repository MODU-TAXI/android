package com.motax.modutaxi.presentation.ui.main.mypage.emailauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.presentation.ui.main.mypage.MyPageAuthBtnState
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

data class MyPageEmailAuthorizationUiState(
    val btnState: MyPageAuthBtnState = MyPageAuthBtnState.Able,
    val time: String = "",
    val isCodeCorrect: Boolean = true
)

sealed class MyPageEmailAuthEvent {
    data object NavigateToComplete : MyPageEmailAuthEvent()
}

@HiltViewModel
class MyPageSchoolAuthorizationEnterCodeViewModel @Inject constructor(
    private val repository: IntroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageEmailAuthorizationUiState())
    val uiState: StateFlow<MyPageEmailAuthorizationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MyPageEmailAuthEvent>()
    val event: SharedFlow<MyPageEmailAuthEvent> = _event.asSharedFlow()

    val certCode = MutableStateFlow("")
    var email = ""

    var curJob: Job? = null

    fun setEmailData(data: String) {
        email = data
        checkTimer()
    }

    private fun checkTimer() {
        curJob = viewModelScope.launch {
            var time = 180
            while (time != 0) {
                delay(1000)
                time--;
                _uiState.update { state ->
                    state.copy(
                        time = "${time / 60}:" + if (time % 60 >= 10) "${time % 60}" else "0${time % 60}"
                    )
                }
            }
            _uiState.update { state ->
                state.copy(
                    btnState = MyPageAuthBtnState.Disable("인증번호 시간 만료"),
                )
            }
        }
    }

    fun checkAuthCode() {
        viewModelScope.launch {

            repository.emailConfirm(certCode.value).onSuccess {
                if (it.isConfirm) {
                    _uiState.update { state ->
                        state.copy(
                            btnState = MyPageAuthBtnState.AuthSuccess("인증번호 검증 성공")
                        )
                    }

                    _event.emit(MyPageEmailAuthEvent.NavigateToComplete)
                } else {
                    _uiState.update { state ->
                        state.copy(
                            btnState = MyPageAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                            isCodeCorrect = false
                        )
                    }
                }
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        btnState = MyPageAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                        isCodeCorrect = false
                    )
                }
            }
        }
    }

    fun resendAuthCode() {
        viewModelScope.launch {
            repository.emailCertificate(email).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        btnState = MyPageAuthBtnState.Able,
                        isCodeCorrect = true

                    )
                }
                curJob?.cancel()
                checkTimer()
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        btnState = MyPageAuthBtnState.AuthFailure("인증번호 재전송 실패")
                    )
                }
            }
        }
    }
}