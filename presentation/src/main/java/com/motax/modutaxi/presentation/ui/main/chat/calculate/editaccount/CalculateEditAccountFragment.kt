package com.motax.modutaxi.presentation.ui.main.chat.calculate.editaccount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.customview.BankBottomSheetDialog
import com.motax.modutaxi.presentation.databinding.FragmentCalculateEditAccountBinding
import com.motax.modutaxi.presentation.ui.main.chat.adapter.RegisteredAccountAdapter
import com.motax.modutaxi.presentation.util.Bank

class CalculateEditAccountFragment :
    BaseFragment<FragmentCalculateEditAccountBinding>(R.layout.fragment_calculate_edit_account) {

    private val viewModel: CalculateEditAccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAccountList.adapter = RegisteredAccountAdapter()
        binding.vm = viewModel
        initStateObserve()
        BankBottomSheetDialog(requireContext(), ::selectBank).show()
    }

    private fun selectBank(bank: Bank) {
        viewModel.selectBank(bank)
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.selectedBank.collect {
                binding.ivSelectedBank.setImageResource(it.logoResId)
                binding.tvSelectedBank.text = it.displayName
            }
        }
    }
}