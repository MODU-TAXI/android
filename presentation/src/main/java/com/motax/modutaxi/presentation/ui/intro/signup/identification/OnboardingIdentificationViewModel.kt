package com.motax.modutaxi.presentation.ui.intro.signup.identification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingIdentificationUiState(
    val focusedField: FocusedField = FocusedField.NONE
)

sealed class OnboardingIdentificationEvent {
    data object NavigateToPhoneAuth : OnboardingIdentificationEvent()
}

@HiltViewModel
class OnboardingIdentificationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingIdentificationUiState())
    val uiState: StateFlow<OnboardingIdentificationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<OnboardingIdentificationEvent>()
    val event: SharedFlow<OnboardingIdentificationEvent> = _event.asSharedFlow()

    val name = MutableStateFlow("")
    val gender = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    val isDataReady = combine(name, gender, phoneNumber) { name, gender, phoneNumber ->
        name.isNotBlank() && gender.isNotBlank() && phoneNumber.isNotBlank()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun updateGender(gender: String) {
        this.gender.value = gender
    }

    fun navigateToPhoneAuth() {
        viewModelScope.launch {
            SignUpData.setSignUpGender(gender.value)
            SignUpData.setSignUpName(name.value)
            SignUpData.setSignUpPhoneNumber(phoneNumber.value)
            _event.emit(OnboardingIdentificationEvent.NavigateToPhoneAuth)
        }
    }

    fun focusNone() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.NONE
            )
        }
    }

    fun focusOnGender() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.GENDER
            )
        }
    }

    fun focusOnName() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.NAME
            )
        }
    }

    fun focusOnPhoneNumber() {
        _uiState.update { state ->
            state.copy(
                focusedField = FocusedField.PHONE
            )
        }
    }
}

enum class FocusedField {
    NONE, NAME, GENDER, PHONE
}