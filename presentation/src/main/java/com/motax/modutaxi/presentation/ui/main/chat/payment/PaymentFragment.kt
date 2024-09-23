package com.motax.modutaxi.presentation.ui.main.chat.payment

import android.content.Intent
import android.net.Uri
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

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is PaymentEvent.NavigateBack -> findNavController().navigateUp()
                    is PaymentEvent.CopyClipBoard -> parentViewModel.copyClipBoard(it.accountString)
                    is PaymentEvent.MoveToToss -> moveToToss()
                }
            }
        }
    }

    private fun moveToToss() {
        val url =
            "supertoss://send?bank=${viewModel.uiState.value.bankName}&accountNo=${viewModel.uiState.value.accountString}&origin=linkgen&amount=${viewModel.uiState.value.charge}"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        // Intent를 처리할 수 있는 앱이 있는지 확인
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            showToastMessage("앱이 설치되어 있지 않습니다")
        }
    }
}