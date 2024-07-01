package com.motax.modutaxi.presentation.ui.main.mypage.usagedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentUsageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsageDetailsFragment : BaseFragment<FragmentUsageDetailsBinding>(R.layout.fragment_usage_details){

    private val viewModel: UsageDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


    }

}