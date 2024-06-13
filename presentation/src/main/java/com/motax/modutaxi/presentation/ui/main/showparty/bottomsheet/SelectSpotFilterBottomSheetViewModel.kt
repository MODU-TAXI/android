package com.motax.modutaxi.presentation.ui.main.showparty.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiSpotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiSpotListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class SelectSpotFilterEvent {
    data class SelectSpot(val id: Long, val name: String) : SelectSpotFilterEvent()
}

@HiltViewModel
class SelectSpotFilterBottomSheetViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiSpotList = MutableStateFlow<List<UiSpotListItem>>(emptyList())
    val uiSpotList: StateFlow<List<UiSpotListItem>> = _uiSpotList.asStateFlow()

    private val _event = MutableSharedFlow<SelectSpotFilterEvent>()
    val event: SharedFlow<SelectSpotFilterEvent> = _event.asSharedFlow()

    fun getSpotList() {
        viewModelScope.launch {
            repository.getNearSpot(100000, 37.4507292, 126.6538126).onSuccess {
                _uiSpotList.value = it.spots.map { data -> data.toUiSpotListItem(::selectSpot) }
            }.onFailure {

            }
        }
    }

    private fun selectSpot(id: Long, name: String) {
        viewModelScope.launch {
            _event.emit(SelectSpotFilterEvent.SelectSpot(id, name))
        }
    }


}