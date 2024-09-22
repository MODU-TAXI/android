package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemCalculateMemberBinding
import com.motax.modutaxi.presentation.databinding.ItemPaymentCheckBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.UiCalculateParticipantItem
import com.motax.modutaxi.presentation.ui.main.chat.model.UiPaymentParticipantItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class PaymentParticipantAdapter :
    ListAdapter<UiPaymentParticipantItem, PaymentParticipantViewHolder>(
        DefaultDiffUtil<UiPaymentParticipantItem>()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PaymentParticipantViewHolder {
        return PaymentParticipantViewHolder(
            ItemPaymentCheckBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PaymentParticipantViewHolder(private val binding: ItemPaymentCheckBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiPaymentParticipantItem) {
        binding.item = item
    }

}