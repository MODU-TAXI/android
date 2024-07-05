package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

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
            val dummyData = UiUsageDetailData(
                historyId = id,
                roomId = 29,
                departureTime = "2024-07-02T17:57:33",
                departureName = "홈플러스",
                arrivalName = "주안역",
                totalCharge = 8900,
                portionCharge = 8900,
                owner = UiUsageParticipantItem(
                    id = 7,
                    nickname = "아르껜지운",
                    name = "(j*)",
                    imageUrl = "https://cdn.modutaxi.shop/testserver/ext/MT_E_2024-07-02-22-04-14.303-uw.jpg",
                    status = "COMPLETE",
                    me = true,
                    portionCharge = 3600
                ),
                participantList = listOf(
                    UiUsageParticipantItem(
                        id = 8,
                        nickname = "참가자1",
                        name = "참가자1",
                        imageUrl = "https://example.com/image1.jpg",
                        status = "COMPLETE",
                        me = false,
                        portionCharge = 3600
                    ),
                    UiUsageParticipantItem(
                        id = 9,
                        nickname = "참가자2",
                        name = "참가자2",
                        imageUrl = "https://example.com/image2.jpg",
                        status = "COMPLETE",
                        me = false,
                        portionCharge = 3600
                    )
                )
            )

            _uiState.update {
                it.copy(
                    usageDetailUiData = dummyData,
                    owner = dummyData.owner,
                    participants = dummyData.participantList
                )
            }
        }
    }
}