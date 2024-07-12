package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiUsageParticipantItem
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiUsageDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsageDetailUiState(
    val usageDetailUiData: UiUsageDetailData = UiUsageDetailData(),
    val owner: UiUsageParticipantItem? = null,
    val participants: List<UiUsageParticipantItem> = emptyList()
)

@HiltViewModel
class UsageDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageDetailUiState())
    val uiState: StateFlow<UsageDetailUiState> = _uiState.asStateFlow()

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
                                    imageUrl = it.imageUrl ?: "", // 기본 URL 설정
                                    status = it.status,
                                    me = it.me,
                                    portionCharge = it.portionCharge
                                )
                            }
                        } else {
                            emptyList()
                        }

                    val usageDetailUiData = UiUsageDetailData(
                        historyId = response.historyId,
                        roomId = response.roomId,
                        departureTime = response.departureTime,
                        departureName = response.departureName,
                        arrivalName = response.arrivalName,
                        totalCharge = response.totalCharge,
                        portionCharge = response.portionCharge,
                    )

                    val uiOwner = owner.let { ownerParticipant ->
                        UiUsageParticipantItem(
                            id = ownerParticipant.id,
                            nickname = ownerParticipant.nickName,
                            name = ownerParticipant.name,
                            imageUrl = ownerParticipant.imageUrl ?: "",
                            status = ownerParticipant.status,
                            me = ownerParticipant.me,
                            portionCharge = response.portionCharge
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
}