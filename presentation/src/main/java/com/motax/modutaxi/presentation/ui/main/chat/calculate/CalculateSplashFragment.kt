package com.motax.modutaxi.presentation.ui.main.chat.calculate

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCalculateSplashBinding
import kotlinx.coroutines.delay

class CalculateSplashFragment: BaseFragment<FragmentCalculateSplashBinding>(R.layout.fragment_calculate_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnStarted {
            delay(2000)
            findNavController().toCalculateEditAmount()
        }
    }

    private fun NavController.toCalculateEditAmount(){
        val action = CalculateSplashFragmentDirections.actionCalculateSplashFragmentToCalculateEditAmountFragment()
        navigate(action)
    }
}