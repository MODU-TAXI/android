package com.motax.modutaxi.presentation.chatmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.chatmanager.model.ChatMessage
import com.motax.modutaxi.presentation.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ChatEvent {
    data class ShowToastMessage(val msg: String) : ChatEvent()
    data class ShowSnackMessage(val msg: String) : ChatEvent()
}

@HiltViewModel
class ChatManager @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel(){

    private val _events: MutableSharedFlow<ChatEvent> = MutableSharedFlow()
    val event: SharedFlow<ChatEvent> = _events

    private val _newChat = MutableSharedFlow<ChatMessage>()
    val newChat: SharedFlow<ChatMessage> = _newChat.asSharedFlow()

    private val chatSocket =
        ChatSocket(::receiveMessage, dataStoreManager)

    private fun receiveMessage(payload: String) {
//        val chatMessage = Gson().fromJson(payload, ChatMessage::class.java)
        Log.d(TAG,payload)

        viewModelScope.launch {
//            _newChat.emit(chatMessage)
        }
    }

    fun sendMessage(roomId:Long, message: String) {
        chatSocket.sendChat(
            roomId,
            "CHAT",
            message
        )
    }

    fun disconnectChat() {
        chatSocket.disconnectServer()
    }

    fun connectChat(roomId: Long){
        chatSocket.connectServer()
        chatSocket.subscribeChat(roomId)
    }

}