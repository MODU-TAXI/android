package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.explanation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentWithdrawalExplanationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExplanationFragment :BaseFragment<FragmentWithdrawalExplanationBinding>(R.layout.fragment_withdrawal_explanation) {

    private val viewModel: ExplanationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initEventObserve()
        viewModel.loadNickname()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it) {
                    is ExplanationEvent.NavigateToReason -> findNavController().toReason()
                }
            }
        }
    }

    private fun NavController.toReason() {
        val action = ExplanationFragmentDirections.actionExplanationToReason()
        navigate(action)
    }
}