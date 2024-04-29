package com.motax.modutaxi.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.model.BaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEvent{
    data object NavigateToOnBoard: LoginEvent()
    data object NavigateToMainActivity: LoginEvent()
    data class ShowToastMessage(val msg: String): LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IntroRepository
): ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    fun kakaoLogin(token: String){
        // todo 유저 회원 가입 됐는지 확인 후 분기 처리
        //_event.emit(LoginEvent.NavigateToMainActivity)

        viewModelScope.launch {

            val loginRequest = LoginRequest(accessToken = token)
            repository.memberLogin("KAKAO", loginRequest).let {
                Log.d("debugging", "뷰모델 진입 성공")
                when(it) {
                    is BaseState.Success -> {
                        _event.emit(LoginEvent.NavigateToOnBoard)
                        _event.emit(LoginEvent.ShowToastMessage("로그인 성공"))
                        Log.d("debugging", "성공")

                    }

                    is BaseState.Error -> {
                        _event.emit(LoginEvent.ShowToastMessage("서버 오류"))
                        Log.d("debugging", "실패")


                    }
                }
            }



        }

    }

}