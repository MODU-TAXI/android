package com.motax.modutaxi.presentation.ui.main.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.domain.repository.AuthRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.chatmanager.model.ChatMessage
import com.motax.modutaxi.presentation.ui.main.chat.mapper.toUiChatMessage
import com.motax.modutaxi.presentation.ui.main.chat.mapper.toUiChatMessageList
import com.motax.modutaxi.presentation.ui.main.chat.model.UiChatMessage
import com.motax.modutaxi.presentation.ui.main.showparty.mapper.toUiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ChatRoomUiState(
    val chatMessage: List<UiChatMessage> = emptyList(),
    val chatInfo: UiTaxiPotListItem = UiTaxiPotListItem{}
)

sealed class ChatRoomEvent{
    data class SendMessage(val msg: String): ChatRoomEvent()
    data class SendImage(val img: String): ChatRoomEvent()
    data object ScrollBottom: ChatRoomEvent()
    data object GoToGallery: ChatRoomEvent()
}

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState: StateFlow<ChatRoomUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ChatRoomEvent>()
    val event: SharedFlow<ChatRoomEvent> = _event.asSharedFlow()

    val chatMessage = MutableStateFlow("")

    private var myId: Long = 0

    init{
        setMyId()
    }

    private fun setMyId(){
        viewModelScope.launch {
            authRepository.getMemberId()?.let{
                myId = it
            }
        }
    }

    fun getMatchInfo(roomId: Long){
        viewModelScope.launch {
            repository.getTaxiPotPreview(roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        chatInfo = it.toUiTaxiPotListItem{}
                    )
                }
            }.onFailure {

            }
        }
    }

    fun getChatMessages(roomId: Long){
        viewModelScope.launch {
            repository.getChatMessages(roomId).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        chatMessage = it.messages.toUiChatMessageList(myId)
                    )
                }

            }.onFailure {

            }
        }
    }

    fun newChatMessage(
        message: ChatMessage
    ){
        val newMessages = uiState.value.chatMessage.toMutableList()
        val newMessage = message.toUiChatMessage(myId)

        if (newMessages.size > 0) {
            val lastMessage = uiState.value.chatMessage.first()

            if (lastMessage.sentTime.isNotBlank() && (lastMessage.memberId == newMessage.memberId && lastMessage.sentTime == newMessage.sentTime)) {
                newMessages.first().sentTime = ""
                newMessage.profileImgUrl = ""
            }
        }

        newMessages.add(0, newMessage)
        _uiState.update { state ->
            state.copy(
                chatMessage = newMessages
            )
        }

        scrollBottom()
    }

    fun sendMessage(){
        viewModelScope.launch {
            _event.emit(ChatRoomEvent.SendMessage(
                chatMessage.value
            ))

            chatMessage.emit("")
        }
    }

    private fun scrollBottom() {
        viewModelScope.launch {
            delay(50)
            _event.emit(ChatRoomEvent.ScrollBottom)
        }
    }

    fun goToGallery(){
        viewModelScope.launch {
            _event.emit(ChatRoomEvent.GoToGallery)
        }
    }
}