package com.motax.modutaxi.presentation.ui.main.notification

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
    val isEmpty: Boolean = false
)

sealed class NotificationEvent {
    data object NavigateToHome : NotificationEvent()
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<NotificationEvent>()
    val event: SharedFlow<NotificationEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()


    fun loadNotifications() {
        viewModelScope.launch {
            val response = repository.getNotifications(0, 10)
            if (response.isSuccess) {
                val notifications = response.getOrNull()?.result?.map {
                    UiNotificationItem(
                        type = it.type,
                        message = it.message,
                        id = it.resourceId,
                        dateTime = it.dateTime.substringBefore("T").replace("-", "."),
                        checked = it.checked
                    )
                } ?: emptyList()

                _uiState.update { currentState ->
                    currentState.copy(
                        notificationList = notifications,
                        isEmpty = notifications.isEmpty()
                    )
                }
            }
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _event.emit(NotificationEvent.NavigateToHome)
        }
    }

}