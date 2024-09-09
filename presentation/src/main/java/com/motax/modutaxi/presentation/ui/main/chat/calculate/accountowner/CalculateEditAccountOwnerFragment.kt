package com.motax.modutaxi.presentation.ui.main.chat.calculate.accountowner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateEditAcocuntOwnerBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateEditAccountOwnerFragment :
    BaseFragment<FragmentCalculateEditAcocuntOwnerBinding>(R.layout.fragment_calculate_edit_acocunt_owner) {

    private val viewModel: CalculateEditAccountOwnerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.ivBank.setImageResource(CalculateForm.bank.logoResId)
        binding.tvBankName.text = CalculateForm.bank.displayName
        binding.tvAccountName.text = CalculateForm.accountString

        binding.ivEditAccountOwner.setOnClickListener {
            binding.etAccountOwner.requestFocus()
        }

        binding.btnConfirm.setOnClickListener {
            CalculateForm.accountOwner = viewModel.ownerName.value
            findNavController().toCalculateConfirm()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getName()
    }

    private fun NavController.toCalculateConfirm() {
        val action =
            CalculateEditAccountOwnerFragmentDirections.actionCalculateEditAccountOwnerFragmentToCalculateConfirmFragment()
        navigate(action)
    }


}