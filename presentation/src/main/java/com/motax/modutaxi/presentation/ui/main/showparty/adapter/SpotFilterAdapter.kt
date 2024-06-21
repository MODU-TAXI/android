package com.motax.modutaxi.presentation.ui.main.showparty.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemSpotBinding
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiSpotListItem
import com.motax.modutaxi.presentation.util.Constants.TAG
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class SpotFilterAdapter :
    ListAdapter<UiSpotListItem, SpotFilterViewHolder>(DefaultDiffUtil<UiSpotListItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotFilterViewHolder =
        SpotFilterViewHolder(
            ItemSpotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: SpotFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SpotFilterViewHolder(private val binding: ItemSpotBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiSpotListItem) {
        Log.d(TAG,item.toString())
        binding.item = item
        binding.root.setOnClickListener {
            item.selectSpot(item.spotId, item.name)
        }
    }
}