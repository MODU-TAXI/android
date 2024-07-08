package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.ItemChatBinding
import com.motax.modutaxi.presentation.databinding.ItemChatImageBinding
import com.motax.modutaxi.presentation.databinding.ItemChatJoinLeaveBinding
import com.motax.modutaxi.presentation.databinding.ItemMychatBinding
import com.motax.modutaxi.presentation.databinding.ItemMychatImageBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.UiChatMessage
import com.motax.modutaxi.presentation.util.Constants.JOIN_LEAVE
import com.motax.modutaxi.presentation.util.Constants.MY_CHAT
import com.motax.modutaxi.presentation.util.Constants.MY_IMAGE_CHAT
import com.motax.modutaxi.presentation.util.Constants.OTHER_CHAT
import com.motax.modutaxi.presentation.util.Constants.OTHER_IMAGE_CHAT

class ChatMessageAdapter :
    ListAdapter<UiChatMessage, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiChatMessage>() {
            override fun areItemsTheSame(
                oldItem: UiChatMessage,
                newItem: UiChatMessage
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UiChatMessage,
                newItem: UiChatMessage
            ): Boolean {
                return false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            MY_CHAT -> {
                MyChatViewHolder(
                    ItemMychatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            OTHER_CHAT -> {
                OtherChatViewHolder(
                    ItemChatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            MY_IMAGE_CHAT -> {
                MyChatImageViewHolder(
                    ItemMychatImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            OTHER_IMAGE_CHAT -> {
                OtherChatImageViewHolder(
                    ItemChatImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            JOIN_LEAVE -> {
                JoinAndLeaveViewHolder(
                    ItemChatJoinLeaveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).type) {
            MY_CHAT -> (holder as MyChatViewHolder).bind(getItem(position))
            OTHER_CHAT -> (holder as OtherChatViewHolder).bind(getItem(position))
            MY_IMAGE_CHAT -> (holder as MyChatImageViewHolder).bind(getItem(position))
            OTHER_IMAGE_CHAT -> (holder as OtherChatImageViewHolder).bind(getItem(position))
            JOIN_LEAVE -> (holder as JoinAndLeaveViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}

class OtherChatViewHolder(private val binding: ItemChatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiChatMessage) {
        binding.root.visibility = View.GONE
        binding.item =item

        if(item.sender == "모두의 택시 봇"){
            binding.ivProfile.setImageResource(R.drawable.ic_chatbot)

            binding.btnAction.visibility = View.VISIBLE
        } else {
            Glide.with(binding.ivProfile.context)
                .load(item.imageUrl)
                .error(R.drawable.ic_person)
                .circleCrop()
                .into(binding.ivProfile)

            binding.btnAction.visibility = View.GONE
        }

        when(item.messageType){
            "CALL_TAXI" -> {
                binding.btnAction.text = "택시 부르러 가기"
            }

            "MATCHING_COMPLETE" -> {
                binding.btnAction.text = "매칭완료"
            }

            "CHAT_BOT" -> {
                binding.btnAction.visibility = View.GONE
            }

            "PAYMENT_REQUEST" -> {
                binding.btnAction.text = "정산완료"
            }

            "PAYMENT_REQUEST_COMPLETE" -> {
                binding.btnAction.text = "정산완료"
            }

            "PAYMENT_COMPLETE" -> {
                binding.btnAction.text = "정산완료"
            }

            "PAYMENT_ALL_COMPLETE" -> {

            }
        }
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
    }

}

class MyChatViewHolder(private val binding: ItemMychatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiChatMessage) {
        binding.root.visibility = View.GONE
        binding.item =item
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
    }
}

class OtherChatImageViewHolder(private val binding: ItemChatImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiChatMessage) {
        binding.root.visibility = View.GONE
        binding.item =item
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
    }

}

class MyChatImageViewHolder(private val binding: ItemMychatImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiChatMessage) {
        binding.root.visibility = View.GONE
        binding.item =item
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
    }

}

class JoinAndLeaveViewHolder(private val binding: ItemChatJoinLeaveBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiChatMessage) {
        binding.root.visibility = View.GONE
        binding.item =item
        binding.executePendingBindings()
        binding.root.visibility = View.VISIBLE
    }
}
