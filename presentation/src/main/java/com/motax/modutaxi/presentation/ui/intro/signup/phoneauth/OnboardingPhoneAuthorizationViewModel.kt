package com.motax.modutaxi.presentation.ui.intro.signup.phoneauth

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

data class PhoneAuthorizationUiState(
    val isButtonEnabled : Boolean = false,
)

@HiltViewModel
class OnboardingPhoneAuthorizationViewModel @Inject constructor(): ViewModel() {


    private val _uiState = MutableStateFlow(PhoneAuthorizationUiState())
    val uiState: StateFlow<PhoneAuthorizationUiState> = _uiState.asStateFlow()

    val authorizationCode = MutableStateFlow("")

    init{
        observeAuthorizationCode()
    }

    private fun observeAuthorizationCode(){
        authorizationCode.onEach {
            if(it.isEmpty()){
                _uiState.update { state ->
                    state.copy(
                        isButtonEnabled = false
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        isButtonEnabled = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }



}