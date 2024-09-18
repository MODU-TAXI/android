package com.motax.modutaxi.presentation.ui.main.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.ui.main.mypage.account.AccountEvent
import com.motax.modutaxi.presentation.ui.main.notification.model.UiNotificationItem
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

data class NotificationUiState(
    val notificationList: List<UiNotificationItem> = emptyList(),
    val hasNext: Boolean = true,
    val page: Int = 0,
    val isEmpty: Boolean = false
)

sealed class NotificationEvent {
    data object NavigateToHome : NotificationEvent()
    data class NavigateToMatchingDetail(val id: Long) : NotificationEvent()
    data object ShowAlertAndNavigateHome: NotificationEvent()
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<NotificationEvent>()
    val event: SharedFlow<NotificationEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    fun refresh(){
        _uiState.update{ state ->
            state.copy(
                notificationList = emptyList(),
                hasNext = true,
                page = 0,
                isEmpty = false
            )
        }

        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            if(uiState.value.hasNext){
                repository.getNotifications(uiState.value.page, 10).onSuccess {
                    _uiState.update { currentState ->
                        currentState.copy(
                            notificationList = uiState.value.notificationList + it.result.map { data ->
                                UiNotificationItem(
                                    type = data.type,
                                    message = data.message,
                                    id = data.resourceId,
                                    dateTime = data.dateTime.substringBefore("T").replace("-", "."),
                                    checked = data.checked,
                                )
                            },
                            hasNext = it.hasNext,
                            page = it.page + 1
                        )
                    }

                    _uiState.update { state ->
                        state.copy(
                            isEmpty = uiState.value.notificationList.isEmpty()
                        )
                    }
                }.onFailure {

                }
            }

        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(NotificationEvent.NavigateToHome)
        }
    }

    fun onNotificationItemClicked(id: Long, type: String) {
        viewModelScope.launch {
            when (type) {
                "REPORT_SUCCESS" -> {
                    // 화면 이동 없음
                    Log.d("debugging", "화면이동없음")
                }
                "PARTICIPATE_REQUEST", "MATCHING_SUCCESS", "MATCHING_COMPLETE",
                "PAYMENT_REQUEST", "PAYMENT_REQUEST_COMPLETE", "PAYMENT_ALL_COMPLETE" -> {
                    val result = repository.getTaxiPotDetail(id)
                    if (result.isSuccess) {
                        _event.emit(NotificationEvent.NavigateToMatchingDetail(id))
                    } else {
                        _event.emit(NotificationEvent.ShowAlertAndNavigateHome)
                    }
                }
                else -> {
                    Log.d("debugging", "알림 이동에러")
                }
            }
        }
    }

}