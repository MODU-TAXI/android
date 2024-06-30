package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentUsageHistoryBinding
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.adapter.UsageHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsageHistoryFragment : BaseFragment<FragmentUsageHistoryBinding>(R.layout.fragment_usage_history) {

    private val viewModel: UsageHistoryViewModel by viewModels()

    private var adapter: UsageHistoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = UsageHistoryAdapter()
        binding.rvUsageHistory.adapter = adapter

        collectUiState()
    }

    private fun collectUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                adapter?.submitList(uiState.monthlyUsageDetailList)
            }
        }
    }
}