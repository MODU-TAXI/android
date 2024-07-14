package com.motax.modutaxi.presentation.ui.main.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemNotificationBinding
import com.motax.modutaxi.presentation.ui.main.notification.model.UiNotificationItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class NotificationAdapter(
) : ListAdapter<UiNotificationItem, NotificationViewHolder>(DefaultDiffUtil<UiNotificationItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NotificationViewHolder(private val binding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiNotificationItem) {
        binding.item = item

    }
}