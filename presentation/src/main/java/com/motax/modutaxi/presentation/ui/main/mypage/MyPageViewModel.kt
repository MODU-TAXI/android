package com.motax.modutaxi.presentation.ui.main.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

    fun onProfileImageClick() {
        Log.d("MyPageViewModel", "프로필 이미지 클릭!!!!!!!!!!")
    }

    fun onProfileChangeClick() {
        Log.d("MyPageViewModel", "프로필 체인지 클릭!!!!!!!!!!")
    }
}