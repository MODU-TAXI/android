package com.motax.modutaxi.presentation.ui.main.chat.calculate.accountowner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateEditAccountOwnerViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val ownerName = MutableStateFlow("")

    fun getName() {
        viewModelScope.launch {

            ownerName.update {
                repository.getMemberName().toString()
            }
        }
    }

}