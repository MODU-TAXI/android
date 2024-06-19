package com.motax.modutaxi.presentation.ui.main.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.chatmanager.ChatManager
import com.motax.modutaxi.presentation.databinding.FragmentChatRoomBinding
import com.motax.modutaxi.presentation.ui.main.chat.adapter.ChatMessageAdapter
import com.motax.modutaxi.presentation.util.ChatState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(R.layout.fragment_chat_room) {

    private val chatManager : ChatManager by activityViewModels()
    private val viewModel: ChatRoomViewModel by viewModels()
    private val args: ChatRoomFragmentArgs by navArgs()
    private val adapter = ChatMessageAdapter()
    private val roomId by lazy { args.id }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ChatState.inChat = true
        binding.vm = viewModel
        binding.rvChat.adapter = adapter
        binding.rvChat.itemAnimator = null
        chatManager.connectChat(roomId)
        viewModel.getMatchInfo(roomId)
        viewModel.getChatMessages(roomId)
        initChatObserve()
        initEventObserve()
    }

    private fun initChatObserve(){
        repeatOnStarted {
            chatManager.newChat.collect{
                viewModel.newChatMessage(it)
            }
        }
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is ChatRoomEvent.SendMessage -> chatManager.sendMessage(roomId, it.msg)
                    is ChatRoomEvent.SendImage -> chatManager.sendImage(roomId, it.img)
                    is ChatRoomEvent.ScrollBottom -> scrollRecyclerViewBottom()
                }
            }
        }
    }

    private fun scrollRecyclerViewBottom() {
        binding.rvChat.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ChatState.inChat = false
        chatManager.disconnectChat()
    }

}