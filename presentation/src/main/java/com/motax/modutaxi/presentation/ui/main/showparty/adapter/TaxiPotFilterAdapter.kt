package com.motax.modutaxi.presentation.ui.main.showparty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.ItemTaxipotFilterBinding
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListFilterItem

class TaxiPotFilterAdapter :
    ListAdapter<UiTaxiPotListFilterItem, TaxiPotFilterViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UiTaxiPotListFilterItem>() {
            override fun areItemsTheSame(
                oldItem: UiTaxiPotListFilterItem,
                newItem: UiTaxiPotListFilterItem
            ): Boolean {
                return oldItem.filter == newItem.filter
            }

            override fun areContentsTheSame(
                oldItem: UiTaxiPotListFilterItem,
                newItem: UiTaxiPotListFilterItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiPotFilterViewHolder =
        TaxiPotFilterViewHolder(
            ItemTaxipotFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TaxiPotFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class TaxiPotFilterViewHolder(private val binding: ItemTaxipotFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiTaxiPotListFilterItem) {
        binding.item = item
        binding.root.setOnClickListener {
            item.onFilterClickLister(item.filter)
        }
    }
}