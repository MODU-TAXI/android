package com.motax.modutaxi.presentation.ui.main.showparty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.createparty.RoomTag
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiTaxiPotMarkerItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListFilterItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotMarkerItem
import com.naver.maps.map.overlay.Marker
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

data class ShowPartyUiState(
    val longitude: Double = 126.6538126,
    val latitude: Double = 37.4507292,
    val isFromSearch: Boolean = false,
    val searchKeyword: String = "",
    val markerDataList: List<UiTaxiPotMarkerItem> = emptyList(),
    val selectedMarkerData: UiTaxiPotMarkerItem = UiTaxiPotMarkerItem(),
    val selectedTaxiPotData: UiTaxiPotListItem = UiTaxiPotListItem() {}
)

data class ShowPartyBottomSheetUiState(
    val page: Int = 0,
    val hasNext: Boolean = true,
    val filterList: List<UiTaxiPotListFilterItem> = emptyList(),
    val taxiPotList: List<UiTaxiPotListItem> = emptyList(),
    val sortType: TaxiPotSortType = TaxiPotSortType.NEW,
    val spotFilter: String = "",
    val roomTagFilter: List<String> = emptyList(),
    val showBottomSheet: Boolean = true
)

sealed class ShowPartyEvent {
    data object MoveToCurLocation : ShowPartyEvent()
    data class SetMarkers(val list: List<UiTaxiPotMarkerItem>) : ShowPartyEvent()
    data object NavigateToSearch : ShowPartyEvent()
    data object NavigateToCreateParty : ShowPartyEvent()
    data class NavigateToMatchDetail(val id: Long) : ShowPartyEvent()
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
                        RoomTag.EMPTY,
                        "거점지", ::setFilter, ::showSpotFilterBottomSheet
                    ),
                    UiTaxiPotListFilterItem(
                        RoomTag.STUDENT_CERTIFICATION,
                        "", ::setFilter, ::showSpotFilterBottomSheet
                    ),
                    UiTaxiPotListFilterItem(
                        RoomTag.ONLY_WOMAN,
                        "", ::setFilter, ::showSpotFilterBottomSheet
                    ),
                    UiTaxiPotListFilterItem(
                        RoomTag.MANNER,
                        "", ::setFilter, ::showSpotFilterBottomSheet
                    ),
                )
            )
        }
    }

    private fun setFilter(filter: RoomTag) {

    }

    private fun showSpotFilterBottomSheet() {

    }

    fun getTaxiPotData(latitude : Double = uiState.value.latitude, longitude: Double = uiState.value.longitude){
        _bottomSheetUiState.update { state ->
            state.copy(
                showBottomSheet = true
            )
        }

        getTaxiPotList(NEW, latitude, longitude)
        getTaxiPotMarkers(latitude, longitude)
    }

    private fun getTaxiPotList(
        type: Int,
        latitude: Double ,
        longitude: Double
    ) {
        if (type == NEW) {
            _bottomSheetUiState.update { state ->
                state.copy(
                    page = 0,
                    hasNext = true
                )
            }
        }

        if (bottomSheetUiState.value.hasNext) {
            val filterMap = hashMapOf<String, Long>()

            if (bottomSheetUiState.value.spotFilter.isNotBlank()) {
                filterMap["spotId"] = spotFilterId
            }

            viewModelScope.launch {
                repository.getTaxiPotList(
                    filterMap,
                    bottomSheetUiState.value.page,
                    10,
                    1500,
                    latitude,
                    longitude,
                    bottomSheetUiState.value.sortType.text,
                    bottomSheetUiState.value.roomTagFilter
                ).onSuccess {
                    val newList = it.result.map { data ->
                        data.toUiTaxiPotListItem(
                            ::navigateToMatchDetail
                        )
                    }
                    _bottomSheetUiState.update { state ->
                        state.copy(
                            page = it.page + 1,
                            hasNext = it.hasNext,
                            taxiPotList = if (type == NEXT_PAGE) bottomSheetUiState.value.taxiPotList + newList else newList
                        )
                    }
                }.onFailure {

                }
            }
        }

    }

    private fun getTaxiPotMarkers(
        latitude: Double,
        longitude: Double
    ) {

        val filterMap = hashMapOf<String, Long>()

        if (bottomSheetUiState.value.spotFilter.isNotBlank()) {
            filterMap["spotId"] = spotFilterId
        }

        viewModelScope.launch {
            repository.getTaxiPotListRadius(
                filterMap,
                1500,
                latitude,
                longitude,
                bottomSheetUiState.value.roomTagFilter
            ).onSuccess {

                val newList = it.rooms.map { data -> data.toUiTaxiPotMarkerItem() }

                _event.emit(ShowPartyEvent.SetMarkers(newList.filter { data ->
                    data !in uiState.value.markerDataList
                }))

                _uiState.update { state ->
                    state.copy(
                        markerDataList = it.rooms.map { data -> data.toUiTaxiPotMarkerItem() },
                        latitude = latitude,
                        longitude = longitude
                    )
                }

            }.onFailure {

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

    fun selectMarker(markerData: UiTaxiPotMarkerItem) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    selectedMarkerData = markerData,
                )
            }

            _bottomSheetUiState.update { state ->
                state.copy(
                    showBottomSheet = false
                )
            }
            getTaxiPotPreview(markerData.roomId)
        }
    }

    private fun getTaxiPotPreview(id: Long) {
        viewModelScope.launch {
            repository.getTaxiPotPreview(id).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        selectedTaxiPotData = it.toUiTaxiPotListItem(::navigateToMatchDetail)
                    )
                }
            }.onFailure {

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
}

enum class TaxiPotSortType(val text: String, val uiText: String) {
    NEW("NEW", "최신순"),
    DISTANCE("DISTANCE", "거리순"),
    ENDTIME("ENDTIME", "시간순")
}