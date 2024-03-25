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

    init{
        onNicknameObserve()
    }

    private fun onNicknameObserve(){
        name.onEach {
            Log.d("debugging", it)
            if(it.isBlank()){
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