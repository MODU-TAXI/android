package com.motax.modutaxi.presentation.ui.main.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.ui.main.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MyPageUiState(
    val isReported: Boolean = false
)

@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun onProfileImageClick() {
        Log.d("MyPageViewModel", "프로필 이미지 클릭!!!!!!!!!!")
    }

    fun onProfileChangeClick() {
        Log.d("MyPageViewModel", "프로필 체인지 클릭!!!!!!!!!!")
    }
}