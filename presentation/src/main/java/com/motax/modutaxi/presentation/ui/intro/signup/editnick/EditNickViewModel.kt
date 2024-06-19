package com.motax.modutaxi.presentation.ui.intro.signup.editnick

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.repository.IntroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EditNickEvent {
    data object NavigateToHowToKnow : EditNickEvent()
}

@HiltViewModel
class EditNickViewModel @Inject constructor(
    private val repository: IntroRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<EditNickEvent>()
    val event: SharedFlow<EditNickEvent> = _event.asSharedFlow()

    val nick = MutableStateFlow("")
    val helperMessage = MutableStateFlow("")
    val isNicknameValid = MutableStateFlow(true)

    init{
        observeNick()
    }

    private fun observeNick(){
        nick.onEach {
            helperMessage.value = ""
        }.launchIn(viewModelScope)
    }

    fun editNick() {
        viewModelScope.launch {
            repository.editNick(nick.value).let {
                when (it) {
                    is BaseState.Success -> {
                        _event.emit(EditNickEvent.NavigateToHowToKnow)
                    }

                    is BaseState.Error -> {
                        helperMessage.value = it.message
                        isNicknameValid.value = false
                    }
                }
            }
        }
    }

}