package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemCalculateMemberBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.UiCalculateParticipantItem
import com.motax.modutaxi.presentation.ui.main.chat.model.UiChatMessage

class CalculateParticipantAdapter :
    ListAdapter<UiCalculateParticipantItem, CalculateParticipantViewHolder>(
        diffCallback
    ) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiCalculateParticipantItem>() {
            override fun areItemsTheSame(
                oldItem: UiCalculateParticipantItem, newItem: UiCalculateParticipantItem
            ): Boolean {
                return oldItem.memberId == newItem.memberId
            }

            override fun areContentsTheSame(
                oldItem: UiCalculateParticipantItem, newItem: UiCalculateParticipantItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CalculateParticipantViewHolder {
        return CalculateParticipantViewHolder(
            ItemCalculateMemberBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CalculateParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CalculateParticipantViewHolder(private val binding: ItemCalculateMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiCalculateParticipantItem) {
        binding.item = item

        binding.btnDelete.setOnClickListener {
            item.changeParticipantState(item)
        }
    }

}