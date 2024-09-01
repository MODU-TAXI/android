package com.motax.modutaxi.presentation.ui.main.chat.calculate.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateConfirmBinding
import com.motax.modutaxi.presentation.ui.formatNumberWithCommas
import com.motax.modutaxi.presentation.ui.main.chat.adapter.CalculateParticipantAdapter
import com.motax.modutaxi.presentation.ui.main.chat.adapter.NonCalculateParticipantAdapter
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalculateConfirmFragment: BaseFragment<FragmentCalculateConfirmBinding>(R.layout.fragment_calculate_confirm) {

    private val viewModel : CalculateConfirmViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvParticipants.itemAnimator = null
        binding.rvNonCalculateMember.itemAnimator = null
        binding.rvParticipants.adapter = CalculateParticipantAdapter()
        binding.rvNonCalculateMember.adapter = NonCalculateParticipantAdapter()
        binding.ivBank.setImageResource(CalculateForm.bank.logoResId)
        binding.tvBankName.text = CalculateForm.bank.displayName
        binding.tvAccount.text = CalculateForm.accountString
    }

}