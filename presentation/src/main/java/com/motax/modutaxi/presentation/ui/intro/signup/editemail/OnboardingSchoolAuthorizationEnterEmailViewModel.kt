package com.motax.modutaxi.presentation.ui.intro.signup.editemail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.usecase.SignUpUseCase
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OnboardingSchoolAuthorizationEnterEmailEvent {
    data class NavigateToEnterCode(val email : String) : OnboardingSchoolAuthorizationEnterEmailEvent()
    data object NavigateToOnboardingComplete : OnboardingSchoolAuthorizationEnterEmailEvent()
    data object GoBackToInit : OnboardingSchoolAuthorizationEnterEmailEvent()
    data class ShowToastMessage(val msg: String) : OnboardingSchoolAuthorizationEnterEmailEvent()
}

@HiltViewModel
class OnboardingSchoolAuthorizationEnterEmailViewModel @Inject constructor(
    private val repository: IntroRepository,
    private val signUpUseCase: SignUpUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _event = MutableSharedFlow<OnboardingSchoolAuthorizationEnterEmailEvent>()
    val event: SharedFlow<OnboardingSchoolAuthorizationEnterEmailEvent> = _event.asSharedFlow()

    val email = MutableStateFlow("")
    val helperText = MutableStateFlow("")
    private val isSignUpSuccess = MutableStateFlow(false)

    val isDataReady =
        combine(email, helperText, isSignUpSuccess) { email, helperText, isSignUpSuccess ->
            email.isNotBlank() && helperText.isBlank() && isSignUpSuccess
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(), false
        )

    init {
        signUp()
        observeEmail()
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpData.key,
                SignUpData.name,
                SignUpData.gender,
                SignUpData.phoneNumber,
                ""
            ).onSuccess {
                dataStoreManager.putAccessToken(it.tokenData.accessToken)
                dataStoreManager.putRefreshToken(it.tokenData.refreshToken)
                isSignUpSuccess.value = true
            }.onFailure {
                _event.emit(OnboardingSchoolAuthorizationEnterEmailEvent.ShowToastMessage("회원가입 실패!"))
                _event.emit(OnboardingSchoolAuthorizationEnterEmailEvent.GoBackToInit)
            }
        }
    }

    private fun observeEmail() {
        email.onEach {
            helperText.value = ""
        }.launchIn(viewModelScope)
    }

    fun emailCertificate() {
        viewModelScope.launch {
            repository.emailCertificate(email.value).onSuccess {
                if (it.isConfirm) {
                    _event.emit(
                        OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToEnterCode(
                            email.value
                        )
                    )
                } else {
                    helperText.value = "올바르지 않은 이메일이에요!"
                }
            }.onFailure {
                helperText.value = "올바르지 않은 이메일이에요!"
            }
        }
    }

    fun navigateToOnboardingComplete() {
        viewModelScope.launch {
            _event.emit(OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToOnboardingComplete)
        }
    }

}