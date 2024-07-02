package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiParticipantItem
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
    val owner: UiParticipantItem = UiParticipantItem(),
    val participants: List<UiParticipantItem> = emptyList()
)
@HiltViewModel
class UsageDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageDetailUiState())
    val uiState: StateFlow<UsageDetailUiState> = _uiState.asStateFlow()

    fun fetchUsageDetails(id: Long) {
        viewModelScope.launch {
            // Here you would call the actual API, for now, we use dummy data
            val dummyData = UsageDetailUiState(
                usageDetailUiData = UiUsageDetailData(
                    departureArrival = "인하대학교 -> 주안역",
                    departureDate = "2024.03.25 18:50",
                    wholeFee = 14450,
                    feePerPerson = 3613
                ),
                owner = UiParticipantItem(
                    profileImage = "https://example.com/image.jpg",
                    nickname = "버스를 놓친 사자"
                ),
                participants = listOf(
                    UiParticipantItem(
                        profileImage = "https://example.com/image1.jpg",
                        nickname = "참가자1"
                    ),
                    UiParticipantItem(
                        profileImage = "https://example.com/image2.jpg",
                        nickname = "참가자2"
                    )
                )
            )
            _uiState.update { dummyData }
        }
    }
}