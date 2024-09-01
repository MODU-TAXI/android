package com.motax.modutaxi.presentation.ui.main.chat.calculate.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.util.Bank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CalculateConfirmUiState(
    val nick: String = "",
)

@HiltViewModel
class CalculateConfirmViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculateConfirmUiState())
    val uiState: StateFlow<CalculateConfirmUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            authRepository.getMemberNickName()?.let{
                _uiState.update { state ->
                    state.copy(
                        nick = it,
                    )
                }
            } ?:run{

            }
        }
    }

    private fun requestCalculate(){
        viewModelScope.launch {
//            repository.requestCalculate().onSuccess {
//
//            }.onFailure {  }
        }
    }

}