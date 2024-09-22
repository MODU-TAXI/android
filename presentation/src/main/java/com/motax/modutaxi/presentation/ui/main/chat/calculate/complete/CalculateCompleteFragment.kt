package com.motax.modutaxi.presentation.ui.main.chat.calculate.complete

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateCompleteBinding
import com.motax.modutaxi.presentation.ui.main.chat.model.CalculateForm

class CalculateCompleteFragment :
    BaseFragment<FragmentCalculateCompleteBinding>(R.layout.fragment_calculate_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            findNavController().toChatFragment()
        }

    }

    private fun NavController.toChatFragment() {
        val action =
            CalculateCompleteFragmentDirections.actionCalculateCompleteFragmentToChatRoomFragment(
                CalculateForm.roomId
            )
        navigate(action)
    }


}