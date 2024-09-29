package com.motax.modutaxi.presentation.ui.main.matchdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiMatchDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiParticipantItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiWaitingMemberItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiMatchDetailData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchDetailUiState(
    val matchDetailUiData: UiMatchDetailData = UiMatchDetailData(),
    val owner: UiParticipantItem = UiParticipantItem(),
    val participants: List<UiParticipantItem> = emptyList(),
    val waitingMembers: List<UiWaitingMemberItem> = emptyList(),
    val roomState: RoomState = RoomState.EMPTY
)

sealed class MatchDetailEvent{
    data object ShowLoading: MatchDetailEvent()
    data object DismissLoading: MatchDetailEvent()
}

enum class RoomState() {
    OWNER,
    PARTICIPANT,
    WAITING,
    NOTHING,
    EMPTY
}

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchDetailUiState())
    val uiState: StateFlow<MatchDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MatchDetailEvent>()
    val event: SharedFlow<MatchDetailEvent> = _event.asSharedFlow()

    private var roomId: Long = 0
    private var memberId: Long = 0

    fun getTaxiPotData(id: Long) {
        roomId = id
        viewModelScope.launch {
            authRepository.getMemberId()?.let{
                 memberId = it
            }
        }
        getTaxiPotDetail(roomId)
    }

    private fun getTaxiPotDetail(roomId: Long) {
        viewModelScope.launch {
            repository.getTaxiPotDetail(roomId).onSuccess {
                val data = it.toUiMatchDetailData()

                _uiState.update { state ->
                    state.copy(
                        matchDetailUiData = data,
                        roomState = if (data.isMyRoom) RoomState.OWNER else if (data.isParticipate) RoomState.PARTICIPANT else if (data.isWaiting) RoomState.WAITING
                        else RoomState.NOTHING
                    )
                }

                getParticipants(roomId)
                getWaitingMembers(roomId)
            }.onFailure {
            }
        }
    }

    private fun getParticipants(roomId: Long) {
        viewModelScope.launch {
            repository.getTaxiPotParticipants(roomId).onSuccess {
                _uiState.update { state ->
                    val members = it.inList.map { data -> data.toUiParticipantItem() }
                    var owner = UiParticipantItem()
                    members.forEach {
                        if (it.memberId == uiState.value.matchDetailUiData.managerId) {
                            owner = it
                        }
                    }

                    state.copy(
                        owner = owner,
                        participants = members.filter {
                            it.memberId != uiState.value.matchDetailUiData.managerId
                        }
                    )
                }

                if (uiState.value.participants.isEmpty()) {
                    _uiState.update { state ->
                        state.copy(
                            participants = listOf(
                                UiParticipantItem(nickname = "현재 참여 멤버가 없어요", isEmpty = true)
                            )
                        )
                    }
                }

            }.onFailure { }
        }
    }

    private fun getWaitingMembers(roomId: Long) {
        viewModelScope.launch {
            repository.getTaxiPotWaitingMembers(roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        waitingMembers = it.waitingList.map { data -> data.toUiWaitingMemberItem(::approveEnterTaxiPot) }
                    )
                }

                if (uiState.value.waitingMembers.isEmpty()) {
                    _uiState.update { state ->
                        state.copy(
                            waitingMembers = listOf(
                                UiWaitingMemberItem(
                                    nickname = "현재 대기 멤버가 없어요",
                                    isEmpty = true
                                ) {}
                            )
                        )
                    }
                }
            }.onFailure {

            }
        }
    }

    fun cancelWaitingMember(roomId: Long){
        viewModelScope.launch {
            _event.emit(MatchDetailEvent.ShowLoading)
            repository.cancelWaitingMembers(roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        waitingMembers = uiState.value.waitingMembers.filter {
                            it.memberId != memberId
                        },
                        roomState = RoomState.NOTHING
                    )
                }
                _event.emit(MatchDetailEvent.DismissLoading)
            }.onFailure {
                _event.emit(MatchDetailEvent.DismissLoading)
            }
        }
    }

    fun enterTaxiPot() {
        viewModelScope.launch {
            _event.emit(MatchDetailEvent.ShowLoading)
            repository.enterTaxiPot(roomId).onSuccess {
                getWaitingMembers(roomId)
                _uiState.update { state ->
                    state.copy(
                        roomState = RoomState.WAITING
                    )
                }
                _event.emit(MatchDetailEvent.DismissLoading)
            }.onFailure {
                _event.emit(MatchDetailEvent.DismissLoading)
            }
        }
    }

    private fun approveEnterTaxiPot(memberId: Long) {
        viewModelScope.launch {
            _event.emit(MatchDetailEvent.ShowLoading)
            repository.approveEnterTaxiPot(roomId, memberId).onSuccess {
                getWaitingMembers(roomId)
                getParticipants(roomId)
                _event.emit(MatchDetailEvent.DismissLoading)
            }.onFailure {
                _event.emit(MatchDetailEvent.DismissLoading)
            }
        }
    }

}