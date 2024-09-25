package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.util.Log
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
import com.motax.modutaxi.presentation.util.Constants.TAG

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
            binding.ivProfile.visibility = View.VISIBLE
            binding.tvNick.visibility = View.VISIBLE
            binding.ivProfile.setImageResource(R.drawable.ic_chatbot)

        } else {
            if(item.profileImgUrl.isBlank()){
                binding.ivProfile.visibility = View.GONE
                binding.tvNick.visibility = View.GONE
            } else {
                binding.ivProfile.visibility = View.VISIBLE
                binding.tvNick.visibility = View.VISIBLE
                Glide.with(binding.ivProfile.context)
                    .load(item.profileImgUrl)
                    .error(R.drawable.ic_person)
                    .circleCrop()
                    .into(binding.ivProfile)
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
        Log.d(TAG,item.toString())
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
