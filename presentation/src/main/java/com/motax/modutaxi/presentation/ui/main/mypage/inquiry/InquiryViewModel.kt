package com.motax.modutaxi.presentation.ui.main.mypage.inquiry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InquiryEvent {
    object OpenKakao : InquiryEvent()
}

@HiltViewModel
class InquiryViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<InquiryEvent>()
    val event: SharedFlow<InquiryEvent> = _event.asSharedFlow()

    fun onKakaoClick() {
        viewModelScope.launch {
            _event.emit(InquiryEvent.OpenKakao)
        }
    }
}