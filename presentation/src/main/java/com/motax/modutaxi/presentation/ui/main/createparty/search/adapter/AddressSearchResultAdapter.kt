package com.motax.modutaxi.presentation.ui.main.createparty.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemSearchResultBinding
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiSearchResultItem
import com.motax.modutaxi.presentation.ui.toDistanceString

class AddressSearchResultAdapter :
    ListAdapter<UiSearchResultItem, AddressSearchResultViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiSearchResultItem>() {
            override fun areItemsTheSame(
                oldItem: UiSearchResultItem,
                newItem: UiSearchResultItem
            ): Boolean {
                return oldItem.placeName == newItem.placeName
            }

            override fun areContentsTheSame(
                oldItem: UiSearchResultItem,
                newItem: UiSearchResultItem
            ): Boolean {
                return oldItem === newItem
            }
        }
    }

    override fun onBindViewHolder(holder: AddressSearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressSearchResultViewHolder = AddressSearchResultViewHolder(
        ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

class AddressSearchResultViewHolder(private val binding: ItemSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiSearchResultItem) {
        binding.item = item
        binding.root.setOnClickListener {
            item.selectLocation(item.latitude, item.longitude, item.placeName, item.placeAddress)
        }
        binding.tvDistance.text = item.distance.toDistanceString()
    }
}