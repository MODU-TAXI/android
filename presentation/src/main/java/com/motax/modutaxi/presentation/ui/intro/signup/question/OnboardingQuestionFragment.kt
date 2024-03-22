package com.motax.modutaxi.presentation.ui.intro.signup.question

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingQuestionHowToKnowAppBinding

class OnboardingQuestionFragment :
    BaseFragment<FragmentOnboardingQuestionHowToKnowAppBinding>(R.layout.fragment_onboarding_question_how_to_know_app) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener()
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toSchoolAuthorizationEnterEmail()
        }
    }

    private fun NavController.toSchoolAuthorizationEnterEmail() {
        val action =
            OnboardingQuestionFragmentDirections.actionQuestionFragmentToSchoolAuthorizationEnterEmail()
        navigate(action)
    }
}