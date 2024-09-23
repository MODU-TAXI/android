package com.motax.modutaxi.presentation.ui.main.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.chatmanager.ChatManager
import com.motax.modutaxi.presentation.customview.EditDeletePopUpMenu
import com.motax.modutaxi.presentation.customview.ExitPopUpMenu
import com.motax.modutaxi.presentation.databinding.FragmentChatRoomBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.chat.adapter.ChatMessageAdapter
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import com.motax.modutaxi.presentation.util.ChatState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(R.layout.fragment_chat_room) {

    private val chatManager: ChatManager by activityViewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ChatRoomViewModel by viewModels()
    private val args: ChatRoomFragmentArgs by navArgs()
    private val adapter = ChatMessageAdapter()
    private val roomId by lazy { args.id }
    private val popupLocation = IntArray(2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CalculateForm.roomId = roomId
        ChatState.inChat = true
        binding.vm = viewModel
        binding.rvChat.adapter = adapter
        binding.rvChat.itemAnimator = null
        chatManager.connectChat(roomId)
        viewModel.getMatchInfo(roomId)
        viewModel.getChatMessages(roomId)
        initChatObserve()
        initEventObserve()
        initImageObserve()

        binding.btnMore.setOnClickListener {
            viewModel.deleteRoom(roomId)
        }
    }

    private fun initChatObserve() {
        repeatOnStarted {
            chatManager.newChat.collect {
                viewModel.newChatMessage(it)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ChatRoomEvent.SendMessage -> chatManager.sendMessage(roomId, it.msg, "CHAT")
                    is ChatRoomEvent.SendImage -> chatManager.sendMessage(roomId, it.img, "IMAGE")
                    is ChatRoomEvent.ScrollBottom -> scrollRecyclerViewBottom()
                    is ChatRoomEvent.GoToGallery -> parentViewModel.goToGallery()
                    is ChatRoomEvent.NavigateToCalculateSplash -> findNavController().toCalculateSplash()
                    is ChatRoomEvent.NavigateToPayment -> findNavController().toPayment()
                    is ChatRoomEvent.NavigateToPaymentState -> findNavController().toPaymentState()
                    is ChatRoomEvent.NavigateToBack -> findNavController().navigateUp()
                    is ChatRoomEvent.ShowPopUp -> showPopup()
                    is ChatRoomEvent.ShowToastMessage -> showToastMessage(it.msg)
                    is ChatRoomEvent.NavigateToHome -> findNavController().toHome()
                    is ChatRoomEvent.ShowParticipantPopUp -> showParticipantPopup()
                }
            }
        }
    }

    private fun initImageObserve() {
        repeatOnStarted {
            parentViewModel.imageUrl.collect {
                chatManager.sendMessage(roomId, it, "IMAGE")
            }
        }
    }

    private fun showPopup() {
        val moreBtn = binding.btnMore
        moreBtn.getLocationOnScreen(popupLocation)
        val left = popupLocation[0] + moreBtn.left.toFloat()
        val top = popupLocation[1] + moreBtn.bottom.toFloat()
        EditDeletePopUpMenu(requireContext(), ::editRoom, ::deleteRoom).show(
            left.toInt(),
            top.toInt()
        )
    }

    private fun showParticipantPopup() {
        val moreBtn = binding.btnMore
        moreBtn.getLocationOnScreen(popupLocation)
        val left = popupLocation[0] + moreBtn.left.toFloat()
        val top = popupLocation[1] + moreBtn.bottom.toFloat()
        ExitPopUpMenu(requireContext(), ::exitRoom).show(
            left.toInt(),
            top.toInt()
        )
    }

    private fun exitRoom(){
        viewModel.exitRoom()
    }

    private fun editRoom() {

    }

    private fun deleteRoom() {
        viewModel.deleteRoom(roomId)
    }

    private fun scrollRecyclerViewBottom() {
        binding.rvChat.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ChatState.inChat = false
        chatManager.disconnectChat()
    }

    private fun NavController.toCalculateSplash() {
        val action = ChatRoomFragmentDirections.actionChatRoomFragmentToCalculateSplashFragment()
        navigate(action)
    }

    private fun NavController.toPayment() {
        val action = ChatRoomFragmentDirections.actionChatRoomFragmentToPaymentFragment()
        navigate(action)
    }

    private fun NavController.toPaymentState() {
        val action = ChatRoomFragmentDirections.actionChatRoomFragmentToPaymentStateFragment()
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = ChatRoomFragmentDirections.actionChatRoomFragmentToHomeFragment()
        navigate(action)
    }

}