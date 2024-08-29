package com.motax.modutaxi.presentation.ui.main.chat.calculate.editamount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CalculateEditAmountEvents {
    data object NavigateToCalculateEditAccount : CalculateEditAmountEvents()
}

@HiltViewModel
class CalculateEditAmountViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<CalculateEditAmountEvents>()
    val event: SharedFlow<CalculateEditAmountEvents> = _event.asSharedFlow()

    val amount = MutableStateFlow("")


    fun navigateToCalculateEditAmount() {
        viewModelScope.launch {
            CalculateForm.totalCharge = amount.value.toInt()
            _event.emit(CalculateEditAmountEvents.NavigateToCalculateEditAccount)
        }
    }


}