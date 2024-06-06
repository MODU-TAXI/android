package com.motax.modutaxi.presentation.ui.main.showparty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemTaxipotFilterBinding
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListFilterItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class TaxiPotFilterAdapter :
    ListAdapter<UiTaxiPotListFilterItem, TaxiPotFilterViewHolder>(DefaultDiffUtil<UiTaxiPotListFilterItem>()) {

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
        if(item.spotName.isNotBlank()){
            binding.tvFilterName.text = item.spotName
            binding.root.setOnClickListener {
                item.onSpotClickListener()
            }
        } else {
            binding.tvFilterName.text = item.filter.uiText
            binding.root.setOnClickListener {
                item.onFilterClickLister(item.filter)
            }
        }
    }
}