package com.motax.modutaxi.presentation.ui.main.chat.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentPaymentBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(R.layout.fragment_payment) {

    private val viewModel: PaymentViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.getPaymentInfo()
        initBankObserve()
        initEventObserve()
    }

    private fun initBankObserve() {
        repeatOnStarted {
            viewModel.bankLogo.collect {
                binding.ivBank.setImageResource(it)
            }
        }
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is PaymentEvent.NavigateBack -> findNavController().navigateUp()
                    is PaymentEvent.CopyClipBoard -> parentViewModel.copyClipBoard(it.accountString)
                }
            }
        }
    }
}