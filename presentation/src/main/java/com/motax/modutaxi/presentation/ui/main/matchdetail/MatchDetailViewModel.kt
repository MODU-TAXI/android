package com.motax.modutaxi.presentation.ui.main.matchdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiMatchDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiMatchDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchDetailUiState(
    val matchDetailUiData: UiMatchDetailData = UiMatchDetailData(),
    val chipItems: List<String> = emptyList()
)

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchDetailUiState())
    val uiState: StateFlow<MatchDetailUiState> = _uiState.asStateFlow()

    fun getRoom(roomId: Long) {
        viewModelScope.launch {
            repository.getRoom(roomId).onSuccess {

                _uiState.update { state ->
                    state.copy(
                        matchDetailUiData = it.toUiMatchDetailData(),
                        chipItems = it.roomTagBitMaskList
                    )
                }
            }.onFailure {

            }
        }
    }


}