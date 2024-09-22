package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemNonCalculateMemberBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.UiCalculateParticipantItem

class NonCalculateParticipantAdapter :
    ListAdapter<UiCalculateParticipantItem, NonCalculateParticipantViewHolder>(
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
    ): NonCalculateParticipantViewHolder {
        return NonCalculateParticipantViewHolder(
            ItemNonCalculateMemberBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NonCalculateParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NonCalculateParticipantViewHolder(private val binding: ItemNonCalculateMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiCalculateParticipantItem) {
        binding.item = item

        binding.btnAdd.setOnClickListener {
            item.changeParticipantState(item)
        }
    }

}