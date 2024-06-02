package com.motax.modutaxi.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemRealtimeTaxipotBinding
import com.motax.modutaxi.presentation.ui.main.home.model.UiRealtimeTaxiPotItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class RealtimeTaxiPotAdapter :
    ListAdapter<UiRealtimeTaxiPotItem, RealtimeTaxiPotViewHolder>(DefaultDiffUtil<UiRealtimeTaxiPotItem>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealtimeTaxiPotViewHolder =
        RealtimeTaxiPotViewHolder(
            ItemRealtimeTaxipotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RealtimeTaxiPotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RealtimeTaxiPotViewHolder(private val binding: ItemRealtimeTaxipotBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiRealtimeTaxiPotItem) {
        binding.item = item
        binding.root.setOnClickListener {
            item.navigateToMatchDetail(item.roomId)
        }
    }
}