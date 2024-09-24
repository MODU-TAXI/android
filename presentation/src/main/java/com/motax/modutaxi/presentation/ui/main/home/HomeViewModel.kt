package com.motax.modutaxi.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.home.mapper.toUiParticipatingTaxiPot
import com.motax.modutaxi.presentation.ui.main.home.model.UiParticipatingTaxiPot
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
import java.time.LocalDate
import javax.inject.Inject

data class HomeUiState(
    val realtimeTaxiPotList: List<UiRealtimeTaxiPotItem> = emptyList(),
    val participatingTaxiPot: UiParticipatingTaxiPot = UiParticipatingTaxiPot(),
    val roomId: String? = null,
    val isParticipating: Boolean = false,
    val memberId: String = "",
    val nickname: String = "",
    val notificationCount: Int = 0,
    val isNotificationExist: Boolean = false,
    val name: String = "",
    val matchingCount: String = "",
    val savePrice: String = "",
    val currentMonth: String = LocalDate.now().monthValue.toString()
)

sealed class HomeEvent {
    data object NavigateToCreateParty : HomeEvent()
    data object NavigateToShowParty : HomeEvent()
    data class NavigateToMatchDetail(val id: Long) : HomeEvent()
    data object NavigateToNotification : HomeEvent()
    data object NavigateToShowPartySearch : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init{
        getNameAndMatchingCount()
        getMonthData()
    }

    private fun getNameAndMatchingCount(){
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    name = authRepository.getMemberName().toString(),
                    matchingCount = authRepository.getMatchingCount().toString() + "회",
                    nickname = authRepository.getMemberNickName().toString()
                )
            }
        }
    }

    private fun getMonthData(){
        viewModelScope.launch {
            repository.getMonthlyUsageHistory(
                LocalDate.now().year,
                LocalDate.now().monthValue
            ).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        savePrice = (it.totalCharge - it.accumulatePortionCharge).formatNumberWithCommas()
                    )
                }
            }.onFailure {

            }
        }
    }

    fun getRealtimeTaxiPots() {
        viewModelScope.launch {
            repository.getTaxiPotList(
                filter = mapOf<String, Long>(),
                page = 0,
                size = 10,
                radius = 5000000,
                longitude = 126.65915614333,
                latitude = 37.450354677762,
                sortType = "NEW",
                roomTags = listOf<String>()
            ).onSuccess { response ->
                _uiState.update { state ->
                    state.copy(
                        realtimeTaxiPotList = response.result.map { responseItem ->
                            UiRealtimeTaxiPotItem(
                                roomId = responseItem.roomId,
                                curHeadCount = responseItem.currentHeadcount,
                                wishHeadCount = responseItem.wishHeadcount,
                                feePerPerson = "${responseItem.expectedChargePerPerson.formatNumberWithCommas()}원",
                                departureSpot = responseItem.departureName,
                                arrivalSpot = responseItem.arrivalName,
                                departTime = responseItem.departureTime,
                                navigateToMatchDetail = ::navigateToMatchDetail
                            )
                        },
                    )
                }

            }
                .onFailure { throwable ->
                    Log.e("debugging", "$throwable")
                }
        }
    }

    fun getIsParticipating() {
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
                            state.copy(participatingTaxiPot = it.toUiParticipatingTaxiPot())
                        }
                    }
                    .onFailure { }
            }
        }
    }

    fun getNotificationCount() {
        viewModelScope.launch {
            repository.getNotificationCounts()
                .onSuccess {
                    Log.d("debugging", "$it.count")
                    _uiState.update { state ->
                        state.copy(
                            notificationCount = it.counts,
                            isNotificationExist = it.counts != 0
                        )
                    }
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

    fun navigateToNotification() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToNotification)
        }
    }

    fun navigateToShowPartySearch() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToShowPartySearch)
        }
    }
}