package com.motax.modutaxi.presentation.ui.intro.signup.phoneauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.usecase.SignUpUseCase
import com.motax.modutaxi.presentation.service.MyFirebaseMessagingService
import com.motax.modutaxi.presentation.ui.intro.signup.AuthBtnState
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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
    val btnState: AuthBtnState = AuthBtnState.Able,
    val time: String = "",
    val isCodeCorrect: Boolean = true
)


sealed class PhoneAuthEvent {
    data object NavigateToEditNick : PhoneAuthEvent()
    data object GoBackToInit : PhoneAuthEvent()
    data class ShowToastMessage(val msg: String) : PhoneAuthEvent()
}

@HiltViewModel
class OnboardingPhoneAuthViewModel @Inject constructor(
    private val repository: IntroRepository,
    private val signUpUseCase: SignUpUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhoneAuthorizationUiState())
    val uiState: StateFlow<PhoneAuthorizationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PhoneAuthEvent>()
    val event: SharedFlow<PhoneAuthEvent> = _event.asSharedFlow()

    val authorizationCode = MutableStateFlow("")
    private val isSignUpSuccess = MutableStateFlow(false)

    var curJob: Job? = null

    fun sendAuthCode() {
        curJob = viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    btnState = AuthBtnState.Able,
                    isCodeCorrect = true
                )
            }

            repository.smsCertificate(SignUpData.key, SignUpData.phoneNumber).onSuccess {
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
                        btnState = AuthBtnState.Disable("인증번호 시간 만료"),
                    )
                }
            }.onFailure {

            }

        }
    }

    fun checkAuthCode() {

        viewModelScope.launch {

            repository.smsConfirm(
                SignUpData.key,
                SignUpData.phoneNumber,
                authorizationCode.value
            ).onSuccess {
                if (it.isConfirm) {
                    _uiState.update { state ->
                        state.copy(
                            btnState = AuthBtnState.AuthSuccess("인증번호 검증 성공")
                        )
                    }

                    signUp()
                } else {
                    _uiState.update { state ->
                        state.copy(
                            btnState = AuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                            isCodeCorrect = false
                        )
                    }
                }
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        btnState = AuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                        isCodeCorrect = false
                    )
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpData.key,
                SignUpData.name,
                SignUpData.gender,
                SignUpData.phoneNumber,
                async { MyFirebaseMessagingService().getFirebaseToken() }.await()
            ).onSuccess {
                authRepository.putAccessToken(it.tokenData.accessToken)
                authRepository.putRefreshToken(it.tokenData.refreshToken)
                authRepository.putGender(it.memberInfoData.gender)
                authRepository.putMemberId(it.memberInfoData.id)
                authRepository.putProfileImg(it.memberInfoData.imageUrl)
                isSignUpSuccess.value = true
                _event.emit(PhoneAuthEvent.NavigateToEditNick)
            }.onFailure {
                _event.emit(PhoneAuthEvent.ShowToastMessage("회원가입 실패!"))
                _event.emit(PhoneAuthEvent.GoBackToInit)
            }
        }
    }

    fun resendAuthCode() {
        curJob?.cancel()
        sendAuthCode()
    }
}