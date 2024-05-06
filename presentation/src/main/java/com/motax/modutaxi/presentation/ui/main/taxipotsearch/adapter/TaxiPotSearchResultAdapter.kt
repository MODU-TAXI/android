package com.motax.modutaxi.presentation.ui.main.taxipotsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemSearchResultBinding
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.model.UiSearchResultItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class TaxiPotSearchResultAdapter :
    ListAdapter<UiSearchResultItem, TaxiPotSearchResultViewHolder>(DefaultDiffUtil<UiSearchResultItem>()) {

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