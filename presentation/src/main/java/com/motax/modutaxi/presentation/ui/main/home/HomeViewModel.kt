package com.motax.modutaxi.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.home.model.UiRealtimeTaxiPotItem
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

data class HomeUiState(
    val realtimeTaxiPotList: List<UiRealtimeTaxiPotItem> = emptyList(),
    val participatingTaxiPot: TaxiPotPreviewData? = null,
    val roomId: String? = null,
    val isParticipating: Boolean = false,
    val isRealtimeTaxipotListEmpty: Boolean = true,
    val memberId: String = "",
    val nickname: String = ""
)

sealed class HomeEvent {
    data object NavigateToCreateParty : HomeEvent()
    data object NavigateToShowParty : HomeEvent()
    data class NavigateToMatchDetail(val id: Long) : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getIsParticipating()
        getRealtimeTaxiPots()
    }

    private fun getRealtimeTaxiPots() {
        viewModelScope.launch {
            repository.getTaxiPotList(
                filter = mapOf<String, Long>(),
                page = 0,
                size = 5,
                radius = 5000000,
                longitude = 126.65915614333,
                latitude = 37.450354677762,
                sortType = "NEW",
                roomTags = listOf<String>()
            ).onSuccess { response ->
                val items = response.rooms.map { responseItem ->
                    UiRealtimeTaxiPotItem(
                        roomId = responseItem.roomId,
                        curHeadCount = responseItem.currentHeadcount,
                        wishHeadCount = responseItem.wishHeadcount,
                        feePerPerson = "${responseItem.expectedChargePerPerson.formatNumberWithCommas()}원",
                        departureSpot = responseItem.departureName,
                        arrivalSpot = responseItem.arrivalName,
                        departTime = responseItem.departureTime,
                        navigateToMatchDetail = { roomId -> navigateToMatchDetail(roomId) }
                    )
                }
                _uiState.update { state ->
                    state.copy(

                        realtimeTaxiPotList = items,
                        isRealtimeTaxipotListEmpty = items.isEmpty()
                    )
                }
            }
                .onFailure { throwable ->
                    Log.e("debugging", "$throwable")
                }
        }
    }

    private fun getIsParticipating() {
        viewModelScope.launch {
            repository.getChatsInfo()
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            roomId = it.roomId,
                            isParticipating = it.roomId != "null",
                            memberId = it.memberId
                        )
                    }
                    getNickname()
                    if (it.roomId != "null") {
                        getParticipatingRoomInfo(it.roomId)
                    }
                }
                .onFailure { }

        }
    }

    private fun getParticipatingRoomInfo(roomId: String?) {
        viewModelScope.launch {
            if (roomId != null) {
                repository.getTaxiPotPreview(roomId.toLong())
                    .onSuccess {
                        _uiState.update { state ->
                            state.copy(participatingTaxiPot = it)
                        }
                    }
                    .onFailure { }
            }
        }
    }

    private fun getNickname() {
        viewModelScope.launch {
            repository.getMemberProfile(_uiState.value.memberId.toLong())
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            nickname = it.nickname
                        )
                    }

                }.onFailure {

                }
        }
    }

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

    fun navigateToMatchDetail(id: Long) {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToMatchDetail(id))
        }
    }
}