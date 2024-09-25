package com.motax.modutaxi.presentation.ui.main.createparty.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemArrivalSpotBinding
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiArrivalSpotItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class AllSpotAdapter :
    ListAdapter<UiArrivalSpotItem, AllSpotViewHolder>(DefaultDiffUtil<UiArrivalSpotItem>()) {


    override fun onBindViewHolder(holder: AllSpotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllSpotViewHolder = AllSpotViewHolder(
        ItemArrivalSpotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

class AllSpotViewHolder(private val binding: ItemArrivalSpotBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiArrivalSpotItem) {
        binding.item = item
        binding.root.setOnClickListener {
            item.selectSpot(item.spotId, item.name, item.longitude, item.latitude)
        }
    }
}