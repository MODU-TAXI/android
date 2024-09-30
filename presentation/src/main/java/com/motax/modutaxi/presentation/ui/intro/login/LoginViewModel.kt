package com.motax.modutaxi.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.usecase.LoginUseCase
import com.motax.modutaxi.domain.usecase.MemberCheckUseCase
import com.motax.modutaxi.presentation.service.MyFirebaseMessagingService
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import com.motax.modutaxi.presentation.util.Constants.TAG
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

    fun login(token: String, type: String) {
        viewModelScope.launch {
            loginUseCase(type, token, async { MyFirebaseMessagingService().getFirebaseToken() }.await()).let{
                when(it){
                    is BaseState.Success -> {
                        authRepository.putAccessToken(it.data.tokenData.accessToken)
                        authRepository.putRefreshToken(it.data.tokenData.refreshToken)
                        authRepository.putMemberId(it.data.memberInfoData.id)
                        authRepository.putMemberName(it.data.memberInfoData.name)
                        authRepository.putMemberGender(it.data.memberInfoData.gender)
                        authRepository.putMemberPhoneNumber(it.data.memberInfoData.phoneNumber)
                        authRepository.putMemberEmail(it.data.memberInfoData.email)
                        authRepository.putMatchingCount(it.data.memberInfoData.matchingCount)
                        authRepository.putMemberBlocked(it.data.memberInfoData.blocked)
                        authRepository.putProfileUrl(it.data.memberInfoData.imageUrl)
                        _event.emit(LoginEvent.ShowToastMessage("로그인 성공"))
                        _event.emit(LoginEvent.NavigateToMainActivity)
                    }

                    is BaseState.Error -> {
                        if(it.code == "MEMBER_004"){
                            SignUpData.setSignUpKey(it.message)
                            _event.emit(LoginEvent.NavigateToOnBoard)
                        } else {
                            _event.emit(LoginEvent.ShowToastMessage(it.message))
                        }

                    }
                }
            }
        }
    }



}