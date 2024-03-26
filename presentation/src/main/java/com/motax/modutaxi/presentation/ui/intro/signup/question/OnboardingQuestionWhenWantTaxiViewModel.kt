package com.motax.modutaxi.presentation.ui.intro.signup.question

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class OnboardQuestionWhenWantTaxiUiState(
    val whenLateSelected : Boolean = false,
    val whenBusLineTooLongSelected : Boolean = false,
    val directSearchSelected : Boolean = false,
    val byEtcSelected : Boolean = false
)

@HiltViewModel
class OnboardingQuestionWhenWantTaxiViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardQuestionWhenWantTaxiUiState())
    val uiState: StateFlow<OnboardQuestionWhenWantTaxiUiState> = _uiState.asStateFlow()

    fun onWhenLate(){
        _uiState.update { state ->
            state.copy(
                whenLateSelected = !uiState.value.whenLateSelected
            )
        }
    }

    fun onWhenBusLineTooLong(){
        _uiState.update { state ->
            state.copy(
                whenBusLineTooLongSelected = !uiState.value.whenBusLineTooLongSelected
            )
        }
    }

    fun onDirectSearchSelected(){
        _uiState.update { state ->
            state.copy(
                directSearchSelected = !uiState.value.directSearchSelected
            )
        }
    }

    fun onByEtcSelected(){
        _uiState.update { state ->
            state.copy(
                byEtcSelected = !uiState.value.byEtcSelected
            )
        }
    }
}