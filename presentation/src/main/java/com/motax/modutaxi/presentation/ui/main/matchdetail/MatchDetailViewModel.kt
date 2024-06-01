package com.motax.modutaxi.presentation.ui.main.matchdetail

import androidx.lifecycle.ViewModel
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ParticipantListUiState(
    val participantList: List<UiParticipantItem> = emptyList(),
    val WaitingMemberList: List<UiWaitingMemberItem> = emptyList()

)

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParticipantListUiState())
    val uiState: StateFlow<ParticipantListUiState> = _uiState.asStateFlow()



}