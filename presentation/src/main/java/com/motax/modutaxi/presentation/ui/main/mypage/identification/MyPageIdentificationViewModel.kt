package com.motax.modutaxi.presentation.ui.main.mypage.identification

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import com.motax.modutaxi.presentation.ui.main.mypage.editnick.MyPageEditNickEvent
import dagger.hilt.android.AndroidEntryPoint
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

data class MyPageIdentificationUiState(
    val myPageFocusedField: MyPageFocusedField = MyPageFocusedField.NONE
)

sealed class MyPageIdentificationEvent {
    data object NavigateToPhoneAuth : MyPageIdentificationEvent()
    data object NavigateToMyPage : MyPageIdentificationEvent()
}

@HiltViewModel
class MyPageIdentificationViewModel @Inject constructor(
    application: Application,
    private val dataStoreManager: DataStoreManager,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MyPageIdentificationUiState())
    val uiState: StateFlow<MyPageIdentificationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MyPageIdentificationEvent>()
    val event: SharedFlow<MyPageIdentificationEvent> = _event.asSharedFlow()

    val name = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    val isDataReady = combine(name, phoneNumber) { name, phoneNumber ->
        name.isNotBlank() && phoneNumber.isNotBlank()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun navigateToPhoneAuth() {
        viewModelScope.launch {
            SignUpData.setSignUpGender(dataStoreManager.getMemberGender().toString())
            SignUpData.setSignUpName(name.value)
            SignUpData.setSignUpPhoneNumber(phoneNumber.value)
            _event.emit(MyPageIdentificationEvent.NavigateToPhoneAuth)
        }
    }

    fun focusNone() {
        _uiState.update { state ->
            state.copy(
                myPageFocusedField = MyPageFocusedField.NONE
            )
        }
    }

    fun focusOnName() {
        _uiState.update { state ->
            state.copy(
                myPageFocusedField = MyPageFocusedField.NAME
            )
        }
    }

    fun focusOnPhoneNumber() {
        _uiState.update { state ->
            state.copy(
                myPageFocusedField = MyPageFocusedField.PHONE
            )
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(MyPageIdentificationEvent.NavigateToMyPage)
        }
    }
}

enum class MyPageFocusedField {
    NONE, NAME, PHONE
}