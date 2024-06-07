package com.motax.modutaxi.presentation.ui.main.matchdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiMatchDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiMatchDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import com.motax.modutaxi.presentation.ui.toRoomTag
import com.motax.modutaxi.presentation.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchDetailUiState(
    val matchDetailUiData: UiMatchDetailData = UiMatchDetailData(),
    val owner: UiParticipantItem = UiParticipantItem(),
    val participants: List<UiParticipantItem> = emptyList(),
    val waitingMembers: List<UiWaitingMemberItem> = emptyList()
)

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchDetailUiState())
    val uiState: StateFlow<MatchDetailUiState> = _uiState.asStateFlow()

    fun getTaxiPotDetail(roomId: Long) {
        viewModelScope.launch {
            repository.getTaxiPotDetail(roomId).onSuccess {

                _uiState.update { state ->
                    state.copy(
                        matchDetailUiData = it.toUiMatchDetailData(),
                    )
                }
            }.onFailure {
                Log.d(TAG,it.message.toString())
            }
        }
    }


}