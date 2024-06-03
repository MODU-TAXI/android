package com.motax.modutaxi.presentation.ui.main.matchdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.matchdetail.mapper.toUiRoomData
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchDetailUiState(
    val managerId: Int = 0,
    val profileImageUrl: String = "",
    val departureDairyDate: String = "",
    val arrivalTime: String = "",
    val arrivalName: String = "",
    val departureName: String = "",
    val departureTime: String = "",
    val expectedChargePerPerson: Int = 0,
    val expectedCharge: Int = 0,
    val myRoom: Boolean = false,
    val participate: Boolean = false,
    val currentHeadcount: Int = 0,
    val wishHeadcount: Int = 0,
    val roomId: Long = 0,
    val chipItems: List<String> = emptyList(),
    val participantList: List<UiParticipantItem> = emptyList()
)

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchDetailUiState())
    val uiState: StateFlow<MatchDetailUiState> = _uiState.asStateFlow()

    private val _chipItems = MutableLiveData<List<String>>();
    val chipItems: LiveData<List<String>> get() = _chipItems

    init {
        _chipItems.value = listOf()
    }

    fun getRoom(roomId: Long) {
        viewModelScope.launch {
            repository.getRoom(roomId).onSuccess {
                _chipItems.value = it.roomTagBitMaskList
                _uiState.value = it.toUiRoomData()
            }
        }
    }


}