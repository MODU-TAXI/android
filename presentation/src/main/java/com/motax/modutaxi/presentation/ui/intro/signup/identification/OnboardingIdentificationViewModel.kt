package com.motax.modutaxi.presentation.ui.intro.signup.identification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class OnboardingIdentificationUiState(
    val isButtonEnabled: Boolean = false
)

@HiltViewModel
class OnboardingIdentificationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingIdentificationUiState())
    val uiState: StateFlow<OnboardingIdentificationUiState> = _uiState.asStateFlow()

    val name = MutableStateFlow("")
    val gender = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    init{
        onNameObserve()
        onGenderObserve()
        onPhoneNumberObserve()
    }

    private fun onNameObserve() {
        name.onEach {
            updateButtonEnabledState()
        }.launchIn(viewModelScope)
    }
    private fun onGenderObserve() {
        gender.onEach {
            updateButtonEnabledState()
        }.launchIn(viewModelScope)
    }

    private fun onPhoneNumberObserve() {
        phoneNumber.onEach {
            updateButtonEnabledState()
        }.launchIn(viewModelScope)
    }

    private fun updateButtonEnabledState() {
        val isEnabled = name.value.isNotBlank() && gender.value.isNotBlank() && phoneNumber.value.isNotBlank()
        Log.d("debugging", gender.value);
        _uiState.update {
            it.copy(isButtonEnabled = isEnabled)
        }
    }

    fun updateGender(gender: String) {
        this.gender.value = gender
    }
}