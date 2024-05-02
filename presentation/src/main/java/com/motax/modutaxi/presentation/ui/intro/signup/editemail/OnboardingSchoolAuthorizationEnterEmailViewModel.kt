package com.motax.modutaxi.presentation.ui.intro.signup.editemail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.IntroRepository
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
    data object NavigateToEnterCode : OnboardingSchoolAuthorizationEnterEmailEvent()
    data object NavigateToOnboardingComplete : OnboardingSchoolAuthorizationEnterEmailEvent()
}

@HiltViewModel
class OnboardingSchoolAuthorizationEnterEmailViewModel @Inject constructor(
    private val repository: IntroRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<OnboardingSchoolAuthorizationEnterEmailEvent>()
    val event: SharedFlow<OnboardingSchoolAuthorizationEnterEmailEvent> = _event.asSharedFlow()

    val email = MutableStateFlow("")
    val helperText = MutableStateFlow("")

    val isDataReady = combine(email, helperText) { email, helperText ->
        email.isNotBlank() && helperText.isBlank()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    init {
        observeEmail()
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
                    _event.emit(OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToEnterCode)
                } else {
                    helperText.value = "올바르지 않은 이메일이에요!"
                }
            }.onFailure {
                helperText.value = "올바르지 않은 이메일이에요!"
            }
        }
    }

    fun navigateToOnboardingComplete(){
        viewModelScope.launch {
            _event.emit(OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToOnboardingComplete)
        }
    }

}