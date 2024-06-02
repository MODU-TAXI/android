package com.motax.modutaxi.presentation.ui.main.matchdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParticipantListUiState(
    val participantList: List<UiParticipantItem> = emptyList()
)

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParticipantListUiState())
    val uiState: StateFlow<ParticipantListUiState> = _uiState.asStateFlow()


    private val _chipItems = MutableLiveData<List<String>>();
    val chipItems: LiveData<List<String>> get() = _chipItems

    init {
        _chipItems.value = listOf("ONLY_WOMAN", "MANNER", "STUDENT_CERTIFICATION")
    }

//    fun getRoom(roomId: Long) {
//        viewModelScope.launch {
//            repository.getRoom(roomId).onSuccess {
//                it.roomTagBitMaskList
//                _chipItems.value = it.roomTagBitMaskList
//                Log.d("success", "success")
//                Log.d("test", "RoomData: $it")
//            }
//        }
//    }


}