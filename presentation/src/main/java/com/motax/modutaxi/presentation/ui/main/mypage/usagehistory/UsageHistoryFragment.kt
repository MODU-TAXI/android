package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentUsageHistoryBinding
import com.motax.modutaxi.presentation.ui.main.home.HomeEvent
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.UsageDetailsFragmentArgs
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

        initEventObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is UsageHistoryEvent.NavigateToUsageDetail -> findNavController().toUsageDetail(it.id)
                    is UsageHistoryEvent.NavigateToMyPage -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun NavController.toUsageDetail(id: Long) {

        val action = UsageHistoryFragmentDirections.actionHistoryToDetail(id)
        navigate(action)
    }

}