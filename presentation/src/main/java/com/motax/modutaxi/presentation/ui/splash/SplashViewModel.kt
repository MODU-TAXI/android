package com.motax.modutaxi.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashUiEvent {
    data object NavigateToMain : SplashUiEvent()
    data object NavigateToIntro : SplashUiEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _event = MutableSharedFlow<SplashUiEvent>()
    val event: SharedFlow<SplashUiEvent> = _event.asSharedFlow()

    fun checkLoginType() {
        viewModelScope.launch {
            authRepository.getRefreshToken()?.let {
                refreshToken(it)
            } ?: run {
                _event.emit(SplashUiEvent.NavigateToIntro)
            }
        }
    }

    private fun refreshToken(token: String) {
        viewModelScope.launch {
            authRepository.refreshToken(token).onSuccess {
                authRepository.putAccessToken(it.tokenData.accessToken)
                authRepository.putRefreshToken(it.tokenData.refreshToken)
                authRepository.putGender(it.memberInfoData.gender)
                authRepository.putMemberId(it.memberInfoData.id)
                authRepository.putProfileImg(it.memberInfoData.imageUrl)
                _event.emit(SplashUiEvent.NavigateToMain)
            }.onFailure {
                authRepository.deleteAccessToken()
                authRepository.deleteRefreshToken()
                authRepository.deleteGender()
                authRepository.deleteMemberId()
                authRepository.deleteProfileImg()
                _event.emit(SplashUiEvent.NavigateToIntro)
            }
        }
    }
}