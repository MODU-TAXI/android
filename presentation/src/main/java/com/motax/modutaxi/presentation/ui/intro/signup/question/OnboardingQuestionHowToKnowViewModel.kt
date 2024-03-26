package com.motax.modutaxi.presentation.ui.intro.signup.question

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class OnboardQuestionUiState(
    val everyTimeSelected : Boolean = false,
    val friendlyRecommendationSelected : Boolean = false,
    val directSearchSelected : Boolean = false,
    val byEtcSelected : Boolean = false
)

@HiltViewModel
class OnboardingQuestionHowToKnowViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardQuestionUiState())
    val uiState: StateFlow<OnboardQuestionUiState> = _uiState.asStateFlow()

    fun onEveryTimeSelect(){
        _uiState.update { state ->
            state.copy(
                everyTimeSelected = !uiState.value.everyTimeSelected
            )
        }
    }

    fun onFriendlyRecommendationSelected(){
        _uiState.update { state ->
            state.copy(
                friendlyRecommendationSelected = !uiState.value.friendlyRecommendationSelected
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