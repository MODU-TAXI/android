package com.motax.modutaxi.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.model.TaxiPotPreviewData
import com.motax.modutaxi.domain.repository.MainRepository
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
    val memberId: String = "",
    val nickname: String = ""
)

sealed class HomeEvent {
    data object NavigateToCreateParty : HomeEvent()
    data object NavigateToShowParty : HomeEvent()

    data object NavigateToSearch : HomeEvent()
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
        loadDummyData()
        getIsParticipating()
    }

    private fun loadDummyData() {
        val dummyData = listOf(
            UiRealtimeTaxiPotItem(
                roomId = 1,
                curHeadCount = 2,
                wishHeadCount = 3,
                feePerPerson = "8,300원",
                departureSpot = "인하대학교",
                arrivalSpot = "주안역",
                departTime = "12:45",
                navigateToMatchDetail = {}),
            UiRealtimeTaxiPotItem(
                roomId = 2,
                curHeadCount = 1,
                wishHeadCount = 3,
                feePerPerson = "9,000원",
                departureSpot = "서울역",
                arrivalSpot = "강남역",
                departTime = "12:45",
                navigateToMatchDetail = {}),
            UiRealtimeTaxiPotItem(
                roomId = 3,
                curHeadCount = 3,
                wishHeadCount = 4,
                feePerPerson = "7,500원",
                departureSpot = "홍대입구",
                arrivalSpot = "명동",
                departTime = "12:45",
                navigateToMatchDetail = {}),
            UiRealtimeTaxiPotItem(
                roomId = 4,
                curHeadCount = 2,
                wishHeadCount = 2,
                feePerPerson = "6,000원",
                departureSpot = "강남역",
                arrivalSpot = "역삼역",
                departTime = "12:45",
                navigateToMatchDetail = {})
        )
        _uiState.value = HomeUiState(realtimeTaxiPotList = dummyData)
    }


    fun getRealtimeTaxiPots() {
        viewModelScope.launch {
            //TODO 리포지토리 불러오기
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
                            Log.d("debugging", "$it")
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

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToSearch)
        }
    }
}