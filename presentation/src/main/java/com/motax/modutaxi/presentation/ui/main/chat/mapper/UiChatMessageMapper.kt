package com.motax.modutaxi.presentation.ui.main.chat.mapper

import com.motax.modutaxi.domain.model.ChatMessageItemData
import com.motax.modutaxi.presentation.chatmanager.model.ChatMessage
import com.motax.modutaxi.presentation.ui.main.chat.model.UiChatMessage
import com.motax.modutaxi.presentation.ui.toChatSentTime
import com.motax.modutaxi.presentation.util.Constants.JOIN_LEAVE
import com.motax.modutaxi.presentation.util.Constants.MY_CHAT
import com.motax.modutaxi.presentation.util.Constants.MY_IMAGE_CHAT
import com.motax.modutaxi.presentation.util.Constants.OTHER_CHAT
import com.motax.modutaxi.presentation.util.Constants.OTHER_IMAGE_CHAT


fun ChatMessage.toUiChatMessage(
    myId: Long
) = UiChatMessage(
    type = when(messageType){
        "JOIN", "LEAVE" -> JOIN_LEAVE
        "IMAGE" -> {
            if(memberId.toLong() == myId){
                MY_IMAGE_CHAT
            } else {
                OTHER_IMAGE_CHAT
            }
        }
        "CHAT" -> {
            if(memberId.toLong() == myId){
                MY_CHAT
            } else {
                OTHER_CHAT
            }
        }
        else -> OTHER_CHAT
    },
    profileImgUrl = imageUrl ?: "",
    messageType = messageType,
    sender = sender,
    content = content,
    imageUrl = imageUrl ?: "",
    sentTime = dateTime.toChatSentTime(),
    memberId = memberId.toLong()
)

fun ChatMessageItemData.toUiChatMessage(
    myId: Long
) = UiChatMessage(
    type = when(messageType){
        "JOIN", "LEAVE" -> JOIN_LEAVE
        "IMAGE" -> {
            if(memberId.toLong() == myId){
                MY_IMAGE_CHAT
            } else {
                OTHER_IMAGE_CHAT
            }
        }
        "CHAT" -> {
            if(memberId.toLong() == myId){
                MY_CHAT
            } else {
                OTHER_CHAT
            }
        }
        else -> OTHER_CHAT
    },
    profileImgUrl = imageUrl ?: "",
    messageType = messageType,
    sender = sender,
    content = content,
    imageUrl = imageUrl ?: "",
    sentTime = dateTime.toChatSentTime(),
    memberId = memberId.toLong()
)

fun List<ChatMessageItemData>.toUiChatMessageList(
    myId: Long
): List<UiChatMessage> {

    val list = map {
        it.toUiChatMessage(myId)
    }.toMutableList().reversed()

    val newList = mutableListOf<UiChatMessage>()

    if(list.size == 1){
        newList.addAll(list)
    } else {
        // DATE 집어넣고, 분까지 같은 메세지는 프로필, 닉네임 생략하는 로직. 시간은 맨 아래 메세지에만 삽입

        for (i in list.indices) {
            if (i + 1 < list.size) {
                val laterChat = list[i]
                val pastChat = list[i + 1]

                if (laterChat.sentTime.isNotBlank() &&
                    laterChat.messageType == pastChat.messageType &&
                    laterChat.memberId == pastChat.memberId &&
                    laterChat.sentTime == pastChat.sentTime
                ) {
                    pastChat.sentTime = ""
                    laterChat.profileImgUrl = ""
                }

                newList.add(laterChat)
            }
        }
    }

    return newList
}