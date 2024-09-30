package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemUsageParticipantBinding
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.model.UiUsageParticipantItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class UsageParticipantAdapter :
    ListAdapter<UiUsageParticipantItem, UsageParticipantViewHolder>(DefaultDiffUtil<UiUsageParticipantItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageParticipantViewHolder =
        UsageParticipantViewHolder(
            ItemUsageParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsageParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UsageParticipantViewHolder(private val binding: ItemUsageParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiUsageParticipantItem) {
        binding.item = item
        binding.root.setOnClickListener {
            if(!item.me) item.onClickListener(item.id)
        }
    }
}