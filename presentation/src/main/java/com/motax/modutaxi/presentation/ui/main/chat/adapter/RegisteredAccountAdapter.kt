package com.motax.modutaxi.presentation.ui.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemRegisteredAccountBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.UiAccountItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class RegisteredAccountAdapter : ListAdapter<UiAccountItem, RegisteredAccountViewHolder>(DefaultDiffUtil<UiAccountItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisteredAccountViewHolder {
        return RegisteredAccountViewHolder(ItemRegisteredAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RegisteredAccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class RegisteredAccountViewHolder(private val binding: ItemRegisteredAccountBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: UiAccountItem){
        binding.tvBankName.text = item.bank.displayName
        binding.ivBank.setImageResource(item.bank.logoResId)
        binding.tvAccountName.text = item.account
        binding.root.setOnClickListener {
            item.selectAccount(item.bank, item.account)
        }
    }

}