package com.motax.modutaxi.presentation.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileBottomSheetUiState(
    val profile: String = "",
    val nick: String = "",
    val certified: Boolean = true,
    val matchingCount: String = ""
)

@HiltViewModel
class ProfileBottomSheetViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileBottomSheetUiState())
    val uiState: StateFlow<ProfileBottomSheetUiState> = _uiState.asStateFlow()

    fun getMemberProfile(id: Long){
        viewModelScope.launch {
            repository.getMemberProfile(id).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        profile = it.imageUrl,
                        nick = it.nickname,
                        certified = it.certified,
                        matchingCount = "${it.matchingCount}회"
                    )
                }
            }.onFailure {

            }
        }
    }
}