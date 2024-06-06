package com.motax.modutaxi.presentation.ui.main.showparty.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemTaxipotBinding
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import com.motax.modutaxi.presentation.util.Constants.TAG
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class TaxiPotListAdapter :
    ListAdapter<UiTaxiPotListItem, TaxiPotListViewHolder>(DefaultDiffUtil<UiTaxiPotListItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiPotListViewHolder =
        TaxiPotListViewHolder(
            ItemTaxipotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )


    override fun onBindViewHolder(holder: TaxiPotListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TaxiPotListViewHolder(private val binding: ItemTaxipotBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiTaxiPotListItem) {
        Log.d(TAG,item.toString())
        binding.item = item
        binding.root.setOnClickListener {
            item.navigateToMatchDetail(item.roomId)
        }
    }
}