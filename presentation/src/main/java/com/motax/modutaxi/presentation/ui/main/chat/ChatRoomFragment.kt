package com.motax.modutaxi.presentation.ui.main.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.chatmanager.ChatManager
import com.motax.modutaxi.presentation.databinding.FragmentChatRoomBinding
import com.motax.modutaxi.presentation.util.Constants.TAG

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(R.layout.fragment_chat_room) {

    private val chatManager : ChatManager by activityViewModels()
    private val args: ChatRoomFragmentArgs by navArgs()
    private val roomId by lazy { args.id }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatManager.connectChat(roomId)
        binding.btnSendMessage.setOnClickListener {
            chatManager.sendMessage(roomId,"")
        }
        initChatObserve()
    }

    private fun initChatObserve(){
        repeatOnStarted {
            chatManager.newChat.collect{
                Log.d(TAG,it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chatManager.disconnectChat()
    }

}