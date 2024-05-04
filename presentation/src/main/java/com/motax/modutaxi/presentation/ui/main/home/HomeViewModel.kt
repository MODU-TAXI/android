package com.motax.modutaxi.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeEvent {
    data object NavigateToCreateParty : HomeEvent()
    data object NavigateToShowParty : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    fun navigateToCreateParty() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToCreateParty)
        }
    }

    fun navigateToShowParty() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToShowParty)
        }
    }
}