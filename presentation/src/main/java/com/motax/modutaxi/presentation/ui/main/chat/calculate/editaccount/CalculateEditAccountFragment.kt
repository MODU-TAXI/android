package com.motax.modutaxi.presentation.ui.main.chat.calculate.editaccount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateEditAccountBinding

class CalculateEditAccountFragment : BaseFragment<FragmentCalculateEditAccountBinding>(R.layout.fragment_calculate_edit_account) {

    private val viewModel : CalculateEditAccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm =viewModel
    }
}