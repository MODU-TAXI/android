package com.motax.modutaxi.presentation.ui.main.mypage.phoneauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.intro.signup.SignUpData
import com.motax.modutaxi.presentation.ui.main.mypage.MyPageAuthBtnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyPagePhoneAuthorizationUiState(
    val btnState: MyPageAuthBtnState = MyPageAuthBtnState.Able,
    val time: String = "",
    val isCodeCorrect: Boolean = true
)


sealed class MyPagePhoneAuthEvent {
    data object NavigateToMyPage : MyPagePhoneAuthEvent()
    data object GoBackToInit : MyPagePhoneAuthEvent()
    data class ShowToastMessage(val msg: String) : MyPagePhoneAuthEvent()
}

@HiltViewModel
class MyPagePhoneAuthViewModel @Inject constructor(
    private val repository: IntroRepository,
    private val mainRepository: MainRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPagePhoneAuthorizationUiState())
    val uiState: StateFlow<MyPagePhoneAuthorizationUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MyPagePhoneAuthEvent>()
    val event: SharedFlow<MyPagePhoneAuthEvent> = _event.asSharedFlow()

    val authorizationCode = MutableStateFlow("")

    var curJob: Job? = null

    fun sendAuthCode() {
        curJob = viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    btnState = MyPageAuthBtnState.Able,
                    isCodeCorrect = true
                )
            }

            repository.smsCertificate(SignUpData.key, SignUpData.phoneNumber).onSuccess {
                var time = 180
                while (time != 0) {
                    delay(1000)
                    time--
                    _uiState.update { state ->
                        state.copy(
                            time = "${time / 60}:" + if (time % 60 >= 10) "${time % 60}" else "0${time % 60}"
                        )
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        btnState = MyPageAuthBtnState.Disable("인증번호 시간 만료"),
                    )
                }
            }.onFailure {

            }

        }
    }

    fun checkAuthCode() {

        viewModelScope.launch {

            repository.smsConfirm(
                SignUpData.key,
                SignUpData.phoneNumber,
                authorizationCode.value
            ).onSuccess {
                if(it.isConfirm){
                    _uiState.update { state ->
                        state.copy(
                            btnState = MyPageAuthBtnState.AuthSuccess("인증번호 검증 성공")
                        )
                    }

                    editProfile()
                } else {
                    _uiState.update { state ->
                        state.copy(
                            btnState = MyPageAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                            isCodeCorrect = false
                        )
                    }
                }
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        btnState = MyPageAuthBtnState.AuthFailure("인증번호가 일치하지 않습니다"),
                        isCodeCorrect = false
                    )
                }
            }
        }
    }

    private fun editProfile() {
        viewModelScope.launch {
            val name = dataStoreManager.getMemberName() ?: ""
            val gender = dataStoreManager.getGender() ?: ""
            val phoneNumber = dataStoreManager.getPhoneNumber() ?: ""
            val imageUrl = dataStoreManager.getProfileUrl() ?: ""

            val profileData = mapOf(
                "name" to name,
                "gender" to gender,
                "phoneNumber" to phoneNumber,
                "imageUrl" to imageUrl
            )

            mainRepository.updateMemberProfile(profileData).onSuccess {

                _event.emit(MyPagePhoneAuthEvent.NavigateToMyPage)
            }.onFailure {
                _event.emit(MyPagePhoneAuthEvent.ShowToastMessage("개인정보 수정 실패!"))
                _event.emit(MyPagePhoneAuthEvent.GoBackToInit)
            }
        }
    }

    fun resendAuthCode() {
        curJob?.cancel()
        sendAuthCode()
    }
}