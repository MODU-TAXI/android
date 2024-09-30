package com.motax.modutaxi.presentation.ui.main.managetaxipot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.extractTimeFromString
import com.motax.modutaxi.presentation.ui.getCurHour
import com.motax.modutaxi.presentation.ui.getCurMinute
import com.motax.modutaxi.presentation.ui.getTodayDate
import com.motax.modutaxi.presentation.ui.getUTCTime
import com.motax.modutaxi.presentation.util.extractMessageFromErrorBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ManageTaxiPotUiState(
    val departureName: String = "",
    val arrivalName: String = "",
    val departureTime: String = "",
    val departureHour: Int = getCurHour(),
    val departureMinute: Int = getCurMinute(),
    val studentCertificationRoomTag: Boolean = false,
    val quiteTag: Boolean = false,
    val todayDate: String = getTodayDate(),
    val isCertified: Boolean = false
)

sealed class ManageTaxiPotEvent {
    data class ShowTimePicker(val hour: Int, val minute: Int) : ManageTaxiPotEvent()
    data object NavigateToDepartureMap : ManageTaxiPotEvent()
    data object NavigateToArrivalSearch : ManageTaxiPotEvent()
    data class NavigateToMatchDetail(val id: Long) : ManageTaxiPotEvent()
    data class ShowToast(val msg: String) : ManageTaxiPotEvent()
    data object NavigateBack : ManageTaxiPotEvent()
    data object ShowLoading : ManageTaxiPotEvent()
    data object DismissLoading : ManageTaxiPotEvent()
}

@HiltViewModel
class ManageTaxiPotViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageTaxiPotUiState())
    val uiState: StateFlow<ManageTaxiPotUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ManageTaxiPotEvent>()
    val event: SharedFlow<ManageTaxiPotEvent> = _event.asSharedFlow()

    val spotId = MutableStateFlow(0L)
    val roomTagBitMask = MutableStateFlow<List<String>>(emptyList())
    val departureLongitude = MutableStateFlow(0.0)
    val departureLatitude = MutableStateFlow(0.0)
    val departureTime = MutableStateFlow("")
    val departureName = MutableStateFlow("")
    val wishHeadCount = MutableStateFlow(WishHeadCount.EMPTY)
    private var roomId: Long = -1L


    val isDataReady = combine(
        spotId,
        departureName,
        departureTime,
        wishHeadCount
    ) { spot, departureName, departureTime, wishHeadCount ->
        spot != 0L && departureName.isNotBlank() && departureTime.isNotBlank() && wishHeadCount != WishHeadCount.EMPTY
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )


    // Edit 모드일때
    fun getRoomInfo(id: Long) {
        roomId = id
        viewModelScope.launch {
            repository.getTaxiPotDetail(id).onSuccess {

                val hour = extractTimeFromString(it.departureTime).first
                val minute = extractTimeFromString(it.departureTime).second
                _uiState.update { state ->
                    state.copy(
                        departureName = it.departureName,
                        arrivalName = it.arrivalName,
                        departureTime = if (hour >= 12) "오후 " + "${hour % 12}시 ${minute}분" else "오전 " + "${hour % 12}시 ${minute}분",
                        departureHour = hour,
                        departureMinute = minute,
                        studentCertificationRoomTag = it.roomTagBitMaskList.contains("STUDENT_CERTIFICATION"),
                        quiteTag = it.roomTagBitMaskList.contains("QUIET"),
                        todayDate = getTodayDate(),
                        isCertified = false
                    )
                }

                departureLongitude.value = it.departureLongitude
                departureLatitude.value = it.departureLatitude
                spotId.value = it.spotId
                departureName.value = it.departureName
                departureTime.value =
                    getUTCTime(uiState.value.departureHour, uiState.value.departureMinute)

                wishHeadCount.value = when (it.wishHeadcount) {
                    2 -> WishHeadCount.ONE
                    3 -> WishHeadCount.TWO
                    4 -> WishHeadCount.THREE
                    else -> WishHeadCount.EMPTY
                }

            }.onFailure {

            }
        }
    }

    fun getMemberSource() {
        viewModelScope.launch {
            authRepository.getMemberId()?.let {
                repository.getMemberProfile(it).onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isCertified = it.certified
                        )
                    }
                }.onFailure { th ->
                    when (th) {
                        is retrofit2.HttpException -> {
                            val message =
                                extractMessageFromErrorBody(th.response()?.errorBody()?.string())
                            _event.emit(ManageTaxiPotEvent.ShowToast(message))
                        }
                    }
                }
            }


        }
    }

    fun createTaxiPot() {
        viewModelScope.launch {
            val roomTag = mutableListOf<String>()
            if (uiState.value.studentCertificationRoomTag) roomTag.add(RoomTag.STUDENT_CERTIFICATION.text)
            if (uiState.value.quiteTag) roomTag.add(RoomTag.QUIET.text)
            _event.emit(ManageTaxiPotEvent.ShowLoading)

            if (roomId == -1L) {
                repository.createTaxiPot(
                    spotId.value,
                    roomTag,
                    departureLongitude.value,
                    departureLatitude.value,
                    departureTime.value,
                    departureName.value,
                    wishHeadCount.value.count
                ).onSuccess {
                    _uiState.value = ManageTaxiPotUiState()
                    _event.emit(ManageTaxiPotEvent.DismissLoading)
                    _event.emit(ManageTaxiPotEvent.NavigateToMatchDetail(it.roomId))
                }.onFailure { th ->
                    when (th) {
                        is retrofit2.HttpException -> {
                            val message =
                                extractMessageFromErrorBody(th.response()?.errorBody()?.string())
                            _event.emit(ManageTaxiPotEvent.ShowToast(message))
                        }
                    }
                    _event.emit(ManageTaxiPotEvent.DismissLoading)
                }
            } else {
                repository.patchRoom(
                    roomId,
                    spotId.value,
                    roomTag,
                    departureLongitude.value,
                    departureLatitude.value,
                    departureTime.value,
                    departureName.value,
                    wishHeadCount.value.count
                ).onSuccess {
                    _uiState.value = ManageTaxiPotUiState()
                    _event.emit(ManageTaxiPotEvent.ShowToast("방 정보 수정 성공"))
                    _event.emit(ManageTaxiPotEvent.DismissLoading)
                    _event.emit(ManageTaxiPotEvent.NavigateBack)
                }.onFailure { th ->
                    when (th) {
                        is retrofit2.HttpException -> {
                            val message =
                                extractMessageFromErrorBody(th.response()?.errorBody()?.string())
                            _event.emit(ManageTaxiPotEvent.ShowToast(message))
                        }
                    }
                    _event.emit(ManageTaxiPotEvent.DismissLoading)
                }
            }


        }
    }

    fun setDepartureInfo(
        latitude: Double,
        longitude: Double,
        name: String
    ) {
        departureLatitude.value = latitude
        departureLongitude.value = longitude
        departureName.value = name

        _uiState.update { state ->
            state.copy(
                departureName = name
            )
        }
    }

    fun setArrivalInfo(
        id: Long,
        name: String
    ) {
        spotId.value = id

        _uiState.update { state ->
            state.copy(
                arrivalName = name
            )
        }
    }

    fun showTimePicker() {
        viewModelScope.launch {
            _event.emit(
                ManageTaxiPotEvent.ShowTimePicker(
                    uiState.value.departureHour,
                    uiState.value.departureMinute
                )
            )
        }
    }

    fun setDepartureTime(hour: Int, minute: Int) {
        _uiState.update { state ->
            state.copy(
                departureHour = hour,
                departureMinute = minute,
                departureTime = if (hour >= 12) "오후 " + "${hour % 12}시 ${minute}분" else "오전 " + "${hour % 12}시 ${minute}분"
            )
        }

        departureTime.value = getUTCTime(hour, minute)
    }

    fun navigateToDepartureMap() {
        viewModelScope.launch {
            _event.emit(ManageTaxiPotEvent.NavigateToDepartureMap)
        }
    }

    fun navigateToArrivalSearch() {
        viewModelScope.launch {
            _event.emit(ManageTaxiPotEvent.NavigateToArrivalSearch)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(ManageTaxiPotEvent.NavigateBack)
        }
    }

    fun selectWishHeadCount(count: WishHeadCount) {
        wishHeadCount.value = count
    }

    fun selectRoomTag(tag: RoomTag) {
        when (tag) {
            RoomTag.STUDENT_CERTIFICATION -> _uiState.update { state ->
                state.copy(studentCertificationRoomTag = !uiState.value.studentCertificationRoomTag)
            }

            RoomTag.QUIET -> _uiState.update { state ->
                state.copy(quiteTag = !uiState.value.quiteTag)
            }

            else -> {

            }
        }
    }

}

enum class WishHeadCount(val count: Int) {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3)
}

enum class RoomTag(val text: String, val uiText: String) {
    EMPTY("", ""),
    ONLY_WOMAN("ONLY_WOMAN", "여자만"),
    ONLY_MAN("ONLY_MAN", "남자만"),
    MANNER("MANNER", "매너탑승"),
    QUIET("QUIET", "조용히"),
    STUDENT_CERTIFICATION("STUDENT_CERTIFICATION", "학생인증")
}
