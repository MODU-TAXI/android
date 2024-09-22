package com.motax.modutaxi.presentation.ui.main.chat.paymentstate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentPaymentStateBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.chat.adapter.PaymentParticipantAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentStateFragment :
    BaseFragment<FragmentPaymentStateBinding>(R.layout.fragment_payment_state) {

    private val viewModel: PaymentStateViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvParticipants.adapter = PaymentParticipantAdapter()
        viewModel.getPaymentInfo()
        initBankObserve()
        binding.btnConfirm.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivClipBoard.setOnClickListener {
            parentViewModel.copyClipBoard(viewModel.uiState.value.accountString)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initBankObserve() {
        repeatOnStarted {
            viewModel.bankLogo.collect {
                binding.ivBank.setImageResource(it)
            }
        }
    }
}