package com.motax.modutaxi.presentation.ui.main.showparty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListFilterItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import com.motax.modutaxi.presentation.util.Constants.TAG
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
import kotlin.math.pow
import kotlin.math.roundToInt

data class ShowPartyUiState(
    val longitude: Double = 126.6538126,
    val latitude: Double = 37.4507292,
    val curZoomLevel: Double = 0.0,
    val isFromSearch: Boolean = false,
    val searchKeyword: String = "",
    val taxiPotList: List<UiTaxiPotListItem> = emptyList(),
    val selectedTaxiPotData: UiTaxiPotListItem = UiTaxiPotListItem() {}
)

data class ShowPartyBottomSheetUiState(
    val filterList: List<UiTaxiPotListFilterItem> = emptyList(),
    val sortType: TaxiPotSortType = TaxiPotSortType.NEW,
    val spotFilter: String = "",
    val roomTagFilter: List<RoomTag> = emptyList(),
    val showBottomSheet: Boolean = true
)

sealed class ShowPartyEvent {
    data object MoveToCurLocation : ShowPartyEvent()
    data class SetMarkers(val list: List<UiTaxiPotListItem>) : ShowPartyEvent()
    data object NavigateToSearch : ShowPartyEvent()
    data object NavigateToCreateParty : ShowPartyEvent()
    data class NavigateToMatchDetail(val id: Long) : ShowPartyEvent()
    data object ShowSpotFilterSheet : ShowPartyEvent()
}

@HiltViewModel
class ShowPartyViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    companion object {
        const val NEW = 0
        const val NEXT_PAGE = 1

        const val STATE_DRAGGING = 1
        const val STATE_SETTLING = 2
        const val STATE_EXPANDED = 3
        const val STATE_COLLAPSED = 4
        const val STATE_HIDDEN = 5
        const val STATE_HALF_EXPANDED = 6
    }

    private val _event = MutableSharedFlow<ShowPartyEvent>()
    val event: SharedFlow<ShowPartyEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ShowPartyUiState())
    val uiState: StateFlow<ShowPartyUiState> = _uiState.asStateFlow()

    private val _bottomSheetUiState = MutableStateFlow(ShowPartyBottomSheetUiState())
    val bottomSheetUiState: StateFlow<ShowPartyBottomSheetUiState> =
        _bottomSheetUiState.asStateFlow()

    val bottomSheetState = MutableStateFlow(STATE_HALF_EXPANDED)
    val bottomSheetHeight = MutableStateFlow(0F)

    private var spotFilterId: Long = 0

    init {
        setBottomSheetFilter()
    }

    fun changeBottomSheetState(state: Int) {
        bottomSheetState.value = state
    }

    fun changeBottomSheetHeight(height: Float) {
        bottomSheetHeight.value = height
    }


    private fun setBottomSheetFilter() {
        _bottomSheetUiState.update { state ->
            state.copy(
                filterList = listOf(
                    UiTaxiPotListFilterItem(
                        RoomTag.STUDENT_CERTIFICATION, false,::setFilter
                    ),
                    UiTaxiPotListFilterItem(
                        RoomTag.ONLY_WOMAN, false,::setFilter
                    ),
                    UiTaxiPotListFilterItem(
                        RoomTag.MANNER, false,::setFilter
                    ),
                )
            )
        }
    }

    private fun setFilter(filter: RoomTag) {

        val newTagFilter = bottomSheetUiState.value.roomTagFilter.toMutableList()
        if (bottomSheetUiState.value.roomTagFilter.contains(filter)) {
            newTagFilter.remove(filter)
        } else {
            newTagFilter.add(filter)
        }

        _bottomSheetUiState.update { state ->
            state.copy(
                filterList = bottomSheetUiState.value.filterList.map {
                    if (it.filter == filter) {
                        it.copy(
                            isClicked = !it.isClicked
                        )
                    } else {
                        it.copy()
                    }
                },
                roomTagFilter = newTagFilter
            )
        }

        getTaxiPotData()
    }

    fun showSpotFilterBottomSheet() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.ShowSpotFilterSheet)
        }
    }

    fun setSpotFilter(id: Long, name: String){
        spotFilterId = id
        _bottomSheetUiState.update { state ->
            state.copy(
                spotFilter = name
            )
        }

        getTaxiPotData()
    }

    fun cancelSpotFilter(){
        spotFilterId = 0
        _bottomSheetUiState.update { state ->
            state.copy(
                spotFilter = ""
            )
        }

        getTaxiPotData()
    }

    fun setZoomLevel(level: Double) {
        _uiState.update { state ->
            state.copy(
                curZoomLevel = level
            )
        }
    }

    fun getTaxiPotData(
        latitude: Double = uiState.value.latitude,
        longitude: Double = uiState.value.longitude
    ) {

        _bottomSheetUiState.update { state ->
            state.copy(
                showBottomSheet = true
            )
        }

        val filterMap = hashMapOf<String, Long>()

        if (bottomSheetUiState.value.spotFilter.isNotBlank()) {
            filterMap["spotId"] = spotFilterId
        }

        viewModelScope.launch {
            repository.getTaxiPotListIntegration(
                filterMap,
                (15000000 * 2.0.pow(-uiState.value.curZoomLevel)).roundToInt(),
                latitude,
                longitude,
                bottomSheetUiState.value.sortType.text,
                bottomSheetUiState.value.roomTagFilter.map { data -> data.text }
            ).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        taxiPotList = it.rooms.map { data ->
                            data.toUiTaxiPotListItem(
                                ::navigateToMatchDetail
                            )
                        }
                    )
                }

                _event.emit(ShowPartyEvent.SetMarkers(uiState.value.taxiPotList))
            }.onFailure {
                Log.d(TAG, it.message.toString())
            }
        }
    }

    fun locationBtnClicked() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.MoveToCurLocation)
        }
    }

    fun selectLocationFromSearch(
        latitude: Double,
        longitude: Double,
        landMark: String,
    ) {
        _uiState.update { state ->
            state.copy(
                latitude = latitude,
                longitude = longitude,
                searchKeyword = landMark,
                isFromSearch = true
            )
        }
    }

    fun selectMarker(data: UiTaxiPotListItem) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    selectedTaxiPotData = data,
                )
            }

            _bottomSheetUiState.update { state ->
                state.copy(
                    showBottomSheet = false
                )
            }
        }
    }

    fun navigateToCreateParty() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToCreateParty)
        }
    }

    fun navigateToSearch() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToSearch)
        }
    }

    fun navigateToMatchDetail(id: Long) {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToMatchDetail(id))
        }
    }

    fun navigateToMatchDetailBySelected() {
        viewModelScope.launch {
            _event.emit(ShowPartyEvent.NavigateToMatchDetail(uiState.value.selectedTaxiPotData.roomId))
        }
    }
}

enum class TaxiPotSortType(val text: String, val uiText: String) {
    NEW("NEW", "최신순"),
    DISTANCE("DISTANCE", "거리순"),
    ENDTIME("ENDTIME", "시간순")
}