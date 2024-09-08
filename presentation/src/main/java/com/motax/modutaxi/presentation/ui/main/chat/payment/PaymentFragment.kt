package com.motax.modutaxi.presentation.ui.main.chat.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentPaymentBinding

class PaymentFragment: BaseFragment<FragmentPaymentBinding>(R.layout.fragment_payment) {

    private val viewModel : PaymentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}