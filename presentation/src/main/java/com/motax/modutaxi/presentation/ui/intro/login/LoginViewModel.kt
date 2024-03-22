package com.motax.modutaxi.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun kakaoLogin(token: String){
        // todo 서버에 accessToken 전송


        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToOnBoard)
//            _event.emit(LoginEvent.NavigateToMainActivity)
//            _event.emit(LoginEvent.ShowToastMessage("서버오류"))
        }

    }

}