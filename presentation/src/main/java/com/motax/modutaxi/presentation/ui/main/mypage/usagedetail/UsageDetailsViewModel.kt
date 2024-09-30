package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiUsageDetailData
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiUsageParticipantItem
import com.motax.modutaxi.presentation.ui.shortenAddress
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

data class UsageDetailUiState(
    val usageDetailUiData: UiUsageDetailData = UiUsageDetailData(),
    val owner: UiUsageParticipantItem = UiUsageParticipantItem(onClickListener = ::empty),
    val participants: List<UiUsageParticipantItem> = emptyList()
)

sealed class UsageDetailEvent {
    data object NavigateToMyPage : UsageDetailEvent()
    data class NavigateToProfile(val id: Long): UsageDetailEvent()
}

fun empty(id: Long){}

@HiltViewModel
class UsageDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageDetailUiState())
    val uiState: StateFlow<UsageDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UsageDetailEvent>()
    val event: SharedFlow<UsageDetailEvent> = _event.asSharedFlow()

    fun loadUsageDetail(id: Long) {
        viewModelScope.launch {

            mainRepository.getUsageDetail(id)
                .onSuccess { response ->
                    val participantsList = response.paymentMemberListData.participantList
                    val owner = participantsList[0]

                    val participants =
                        if (participantsList.size > 1) {
                            participantsList.drop(1).map {
                                UiUsageParticipantItem(
                                    id = it.id,
                                    nickname = it.nickName,
                                    name = it.name,
                                    imageUrl = it.imageUrl ?: "",
                                    status = if (it.status == "COMPLETE") "완료" else "미완료",
                                    me = it.me,
                                    onClickListener = ::navigateToParticipantProfile
                                )
                            }
                        } else {
                            emptyList()
                        }

                    val usageDetailUiData = UiUsageDetailData(
                        historyId = response.historyId,
                        roomId = response.roomId,
                        departureTime = response.departureTime,
                        departureName = response.departureName.shortenAddress(),
                        arrivalName = response.arrivalName,
                        totalCharge = response.totalCharge.formatNumberWithCommas(),
                        portionCharge = response.portionCharge.formatNumberWithCommas(),
                    )

                    val uiOwner = owner.let { ownerParticipant ->
                        UiUsageParticipantItem(
                            id = ownerParticipant.id,
                            nickname = ownerParticipant.nickName,
                            name = ownerParticipant.name,
                            imageUrl = ownerParticipant.imageUrl ?: "",
                            status = ownerParticipant.status,
                            me = ownerParticipant.me,
                            onClickListener = ::empty
                        )
                    }

                    _uiState.update { currentState ->
                        currentState.copy(
                            usageDetailUiData = usageDetailUiData,
                            owner = uiOwner,
                            participants = participants
                        )
                    }
                }
                .onFailure { }
        }
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            _event.emit(UsageDetailEvent.NavigateToMyPage)
        }
    }

    fun navigateToOwnerProfile(){
        viewModelScope.launch {
            if(!uiState.value.owner.me){
                _event.emit(UsageDetailEvent.NavigateToProfile(uiState.value.owner.id))
            }
        }
    }

    private fun navigateToParticipantProfile(id: Long){
        viewModelScope.launch {
            _event.emit(UsageDetailEvent.NavigateToProfile(id))
        }
    }
}