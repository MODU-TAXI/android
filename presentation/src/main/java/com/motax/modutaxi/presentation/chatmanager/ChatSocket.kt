package com.motax.modutaxi.presentation.chatmanager

import android.annotation.SuppressLint
import android.util.Log
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.presentation.util.Constants.TAG
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompHeader
import java.time.LocalDateTime

class ChatSocket(
    private val acceptChat: (String) -> Unit,
    private val dataStoreManager: DataStoreManager
) {

    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://test.modutaxi.shop:8181/ws")

    fun connectServer() {
        try{
            val headerList = arrayListOf<StompHeader>()
            val jwt = runBlocking {
                dataStoreManager.getAccessToken()
            }

            jwt?.let {
                headerList.add(StompHeader("token", it))
            } ?: run {

            }

            stompClient.connect(headerList)
        } catch(e: Exception){
            Log.d(TAG,e.message.toString())
        }
    }

    fun disconnectServer(){
        stompClient.disconnect()
    }

    @SuppressLint("CheckResult")
    fun subscribeChat(roomId: Long) {
        try{
            stompClient.topic("/sub/chat/$roomId").subscribe { topicMessage ->
                acceptChat(topicMessage.payload)
            }
        } catch(e: Exception){
            Log.d(TAG,e.message.toString())
        }
    }

    fun sendChat(roomId: Long, messageType: String, content: String) {
        try{
            val data = JSONObject()
            data.put("roomId",roomId)
            data.put("type",messageType)
            data.put("content",content)
            stompClient.send("/pub/chat", data.toString()).subscribe()
        } catch(e: Exception){
            Log.d(TAG,e.message.toString())
        }
    }
}