package com.motax.modutaxi.presentation.ui.main.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemNotificationBinding
import com.motax.modutaxi.presentation.ui.main.notification.NotificationViewModel
import com.motax.modutaxi.presentation.ui.main.notification.model.UiNotificationItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class NotificationAdapter(
    private val viewModel: NotificationViewModel
) : ListAdapter<UiNotificationItem, NotificationViewHolder>(DefaultDiffUtil<UiNotificationItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NotificationViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NotificationViewHolder(
    private val binding: ItemNotificationBinding,
    private val viewModel: NotificationViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiNotificationItem) {
        binding.item = item
        binding.root.setOnClickListener {
            viewModel.onNotificationItemClicked(item.id, item.type)
        }

    }
}