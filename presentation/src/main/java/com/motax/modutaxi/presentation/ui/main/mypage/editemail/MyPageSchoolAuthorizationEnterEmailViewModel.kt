package com.motax.modutaxi.presentation.ui.main.mypage.editemail

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

sealed class MyPageSchoolAuthorizationEnterEmailEvent {
    data class NavigateToEnterCode(val email : String) : MyPageSchoolAuthorizationEnterEmailEvent()
    data object NavigateToMyPage : MyPageSchoolAuthorizationEnterEmailEvent()
    data class ShowToastMessage(val msg: String) : MyPageSchoolAuthorizationEnterEmailEvent()
}

@HiltViewModel
class MyPageSchoolAuthorizationEnterEmailViewModel @Inject constructor(
    private val repository: IntroRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<MyPageSchoolAuthorizationEnterEmailEvent>()
    val event: SharedFlow<MyPageSchoolAuthorizationEnterEmailEvent> = _event.asSharedFlow()

    val email = MutableStateFlow("")
    val helperText = MutableStateFlow("")

    val isDataReady =
        combine(email, helperText) { email, helperText ->
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
                    _event.emit(
                        MyPageSchoolAuthorizationEnterEmailEvent.NavigateToEnterCode(
                            email.value
                        )
                    )
                } else {
                    helperText.value = "올바르지 않은 이메일이에요!"
                }
            }.onFailure {
                helperText.value = "올바르지 않은 이메일이에요!"
            }
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(MyPageSchoolAuthorizationEnterEmailEvent.NavigateToMyPage)
        }
    }

}