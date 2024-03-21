package com.motax.modutaxi.presentation.ui.intro

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingIdentificationBinding

class OnboardingIdentificationFragment :
    BaseFragment<FragmentOnboardingIdentificationBinding>(R.layout.fragment_onboarding_identification) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBtnListener();
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toQuestion()
        }
    }

    private fun NavController.toQuestion() {
        val action = OnboardingIdentificationFragmentDirections.actionIdentificationFragmentToQuestionFragment()
        navigate(action)
    }
}