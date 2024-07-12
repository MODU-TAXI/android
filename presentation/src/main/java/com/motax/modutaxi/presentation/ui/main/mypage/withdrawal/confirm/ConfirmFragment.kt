package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentWithdrawalConfirmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmFragment : BaseFragment<FragmentWithdrawalConfirmBinding>(R.layout.fragment_withdrawal_confirm){

    private val viewModel: ConfirmViewModel by viewModels()

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
                    is ConfirmEvent.NavigateToComplete -> findNavController().toComplete()
                }
            }
        }
    }

    private fun NavController.toComplete() {
        val action = ConfirmFragmentDirections.actionConfirmToComplete()
        navigate(action)
    }
}