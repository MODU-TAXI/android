package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentUsageDetailsBinding
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.adapter.UsageParticipantAdapter
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.UsageHistoryEvent
import com.motax.modutaxi.presentation.ui.toProfileBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsageDetailsFragment :
    BaseFragment<FragmentUsageDetailsBinding>(R.layout.fragment_usage_details) {

    private val viewModel: UsageDetailsViewModel by viewModels()
    private val args: UsageDetailsFragmentArgs by navArgs()
    private val roomId by lazy { args.id }
    private var adapter: UsageParticipantAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = UsageParticipantAdapter()
        binding.rvParticipants.adapter = adapter

        viewModel.loadUsageDetail(roomId)

        initEventObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is UsageDetailEvent.NavigateToMyPage -> findNavController().navigateUp()
                    is UsageDetailEvent.NavigateToProfile -> findNavController().toProfileBottomSheet(
                        it.id,
                        roomId
                    )
                }
            }
        }
    }

}