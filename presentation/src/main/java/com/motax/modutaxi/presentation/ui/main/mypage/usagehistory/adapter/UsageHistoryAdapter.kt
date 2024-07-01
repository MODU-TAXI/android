package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemUsageHistoryBinding
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.model.UsageHistoryUiItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class UsageHistoryAdapter :
    ListAdapter<UsageHistoryUiItem, UsageDetailViewHolder>(DefaultDiffUtil<UsageHistoryUiItem>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageDetailViewHolder =
        UsageDetailViewHolder(
            ItemUsageHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsageDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UsageDetailViewHolder(private val binding: ItemUsageHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UsageHistoryUiItem) {
        binding.item = item
        binding.root.setOnClickListener {
            //item.navigateToUsageDetail(item.historyId)
        }
    }
}