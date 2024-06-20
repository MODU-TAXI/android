package com.motax.modutaxi.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.usecase.LoginUseCase
import com.motax.modutaxi.domain.usecase.MemberCheckUseCase
import com.motax.modutaxi.presentation.service.MyFirebaseMessagingService
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEvent {
    data object NavigateToOnBoard : LoginEvent()
    data object NavigateToMainActivity : LoginEvent()
    data class ShowToastMessage(val msg: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val memberCheckUseCase: MemberCheckUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun memberCheck(token: String) {
        viewModelScope.launch {
            memberCheckUseCase("KAKAO", token, async { MyFirebaseMessagingService().getFirebaseToken() }.await()).onSuccess {
                if (it.existent) {
                    kakaoLogin(token)
                } else {
                    SignUpData.setSignUpKey(it.key)
                    _event.emit(LoginEvent.NavigateToOnBoard)
                }
            }.onFailure {
                _event.emit(LoginEvent.ShowToastMessage(it.message.toString()))
            }
        }
    }

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            loginUseCase("KAKAO", token, async { MyFirebaseMessagingService().getFirebaseToken() }.await()).onSuccess {
                _event.emit(LoginEvent.ShowToastMessage("로그인 성공"))
                authRepository.putAccessToken(it.tokenData.accessToken)
                authRepository.putRefreshToken(it.tokenData.refreshToken)
                authRepository.putGender(it.memberInfoData.gender)
                authRepository.putMemberId(it.memberInfoData.id)
                authRepository.putProfileImg(it.memberInfoData.imageUrl)
                _event.emit(LoginEvent.NavigateToMainActivity)
            }.onFailure {
                _event.emit(LoginEvent.ShowToastMessage(it.message.toString()))
            }
        }
    }

}