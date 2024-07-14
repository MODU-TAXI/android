package com.motax.modutaxi.presentation.ui.main.mypage.editnick

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

sealed class MyPageEditNickEvent {
    data object NavigateToMyPage : MyPageEditNickEvent()
    data class ShowToastMessage(val msg: String) : MyPageEditNickEvent()
}

@HiltViewModel
class MyPageEditNickViewModel @Inject constructor(
    private val repository: IntroRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<MyPageEditNickEvent>()
    val event: SharedFlow<MyPageEditNickEvent> = _event.asSharedFlow()

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
                        _event.emit(MyPageEditNickEvent.NavigateToMyPage)
                        _event.emit(MyPageEditNickEvent.ShowToastMessage("닉네임 수정에 성공했습니다."))
                    }

                    is BaseState.Error -> {
                        helperMessage.value = it.message
                        isNicknameValid.value = false
                    }
                }
            }
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(MyPageEditNickEvent.NavigateToMyPage)
        }
    }
}