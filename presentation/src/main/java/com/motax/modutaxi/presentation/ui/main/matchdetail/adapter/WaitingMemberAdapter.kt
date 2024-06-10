package com.motax.modutaxi.presentation.ui.main.matchdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemWaitingMemberBinding
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class WaitingMemberAdapter :
    ListAdapter<UiWaitingMemberItem, WaitingMemberViewHolder>(DefaultDiffUtil<UiWaitingMemberItem>()) {

    override fun onBindViewHolder(holder: WaitingMemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitingMemberViewHolder =
        WaitingMemberViewHolder(
            ItemWaitingMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}


class WaitingMemberViewHolder(private val binding: ItemWaitingMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiWaitingMemberItem) {
        binding.item = item
        binding.tvMatchingCount.text =  "(${item.matchingCount})"
        binding.btnAcceptEnter.setOnClickListener {
            item.acceptParticipant(item.memberId)
        }
    }
}