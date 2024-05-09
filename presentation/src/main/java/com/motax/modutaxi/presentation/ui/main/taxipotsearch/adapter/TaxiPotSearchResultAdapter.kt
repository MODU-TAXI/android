package com.motax.modutaxi.presentation.ui.main.taxipotsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemSearchResultBinding
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.model.UiSearchResultItem

class TaxiPotSearchResultAdapter :
    ListAdapter<UiSearchResultItem, TaxiPotSearchResultViewHolder>(diffCallback) {

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

    override fun onBindViewHolder(holder: TaxiPotSearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaxiPotSearchResultViewHolder = TaxiPotSearchResultViewHolder(
        ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

class TaxiPotSearchResultViewHolder(private val binding: ItemSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiSearchResultItem) {
        binding.item = item
    }
}