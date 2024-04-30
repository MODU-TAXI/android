package com.motax.modutaxi.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    fun kakaoLogin(token: String) {
        // todo 유저 회원 가입 됐는지 확인 후 분기 처리

        viewModelScope.launch {
            loginUseCase("KAKAO", token).onSuccess {
                _event.emit(LoginEvent.ShowToastMessage("로그인 성공"))
                _event.emit(LoginEvent.NavigateToOnBoard)
            }.onFailure {
                _event.emit(LoginEvent.ShowToastMessage(it.message.toString()))
            }
        }
    }
}