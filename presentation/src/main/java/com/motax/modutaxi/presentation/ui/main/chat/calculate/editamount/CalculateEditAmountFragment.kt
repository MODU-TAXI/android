package com.motax.modutaxi.presentation.ui.main.chat.calculate.editamount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateEditAmountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateEditAmountFragment :
    BaseFragment<FragmentCalculateEditAmountBinding>(R.layout.fragment_calculate_edit_amount) {

    private val viewModel: CalculateEditAmountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is CalculateEditAmountEvents.NavigateToCalculateEditAccount -> findNavController().toCalculateEditAccount()
                    is CalculateEditAmountEvents.NavigateBack -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun NavController.toCalculateEditAccount() {
        val action =
            CalculateEditAmountFragmentDirections.actionCalculateEditAmountFragmentToCalculateEditAccountFragment()
        navigate(action)
    }


}