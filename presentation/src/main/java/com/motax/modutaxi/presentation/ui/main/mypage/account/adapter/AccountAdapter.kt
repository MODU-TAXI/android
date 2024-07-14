package com.motax.modutaxi.presentation.ui.main.mypage.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motax.modutaxi.presentation.databinding.ItemAccountBinding
import com.motax.modutaxi.presentation.ui.main.mypage.account.AccountViewModel
import com.motax.modutaxi.presentation.ui.main.mypage.account.model.UiAccountItem
import com.motax.modutaxi.presentation.util.DefaultDiffUtil

class AccountAdapter(
    private val viewModel: AccountViewModel
) : ListAdapter<UiAccountItem, AccountViewHolder>(DefaultDiffUtil<UiAccountItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(
            ItemAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            viewModel
        )

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AccountViewHolder(private val binding: ItemAccountBinding, private val viewModel: AccountViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiAccountItem) {
        binding.item = item

        binding.ivDelete.setOnClickListener {
            viewModel.deleteAccount(item)
        }
    }
}