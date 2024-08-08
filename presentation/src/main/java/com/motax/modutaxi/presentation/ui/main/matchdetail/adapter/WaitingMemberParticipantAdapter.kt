package com.motax.modutaxi.presentation.ui.main.matchdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.ItemWaitingMemberParticipantBinding
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiWaitingMemberItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class WaitingMemberParticipantAdapter :
    ListAdapter<UiWaitingMemberItem, WaitingMemberParticipantViewHolder>(DefaultDiffUtil<UiWaitingMemberItem>()) {

    override fun onBindViewHolder(holder: WaitingMemberParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitingMemberParticipantViewHolder =
        WaitingMemberParticipantViewHolder(
            ItemWaitingMemberParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}


class WaitingMemberParticipantViewHolder(private val binding: ItemWaitingMemberParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiWaitingMemberItem) {

        binding.item = item

        if (item.isEmpty) {
            binding.tvRoomManagerNickname.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.mx_gray500
                )
            )
        }
    }
}