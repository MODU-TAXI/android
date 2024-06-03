package com.motax.modutaxi.presentation.ui.main.matchdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemParticipantBinding
import com.motax.modutaxi.presentation.ui.main.matchdetail.model.UiParticipantItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil


class ParticipantAdapter :
    ListAdapter<UiParticipantItem, ParticipantViewHolder>(DefaultDiffUtil<UiParticipantItem>()) {

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder =
        ParticipantViewHolder(
            ItemParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}


class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiParticipantItem) {
        binding.item = item
        binding.executePendingBindings()
    }
}