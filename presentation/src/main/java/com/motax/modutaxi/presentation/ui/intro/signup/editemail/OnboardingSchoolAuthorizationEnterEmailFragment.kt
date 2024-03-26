package com.motax.modutaxi.presentation.ui.intro.signup.editemail

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingSchoolAuthorizationEnterEmailBinding

class OnboardingSchoolAuthorizationEnterEmailFragment :
    BaseFragment<FragmentOnboardingSchoolAuthorizationEnterEmailBinding>(
        R.layout.fragment_onboarding_school_authorization_enter_email
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener()
    }

    private fun setBtnListener() {
        binding.btnAuthorizationNextTime.setOnClickListener {
            findNavController().toOnboardingComplete()
        }
        binding.btnAuthorizationCheck.setOnClickListener {
            findNavController().toEmailAuth()
        }
    }

    private fun NavController.toOnboardingComplete() {
        val action =
            OnboardingSchoolAuthorizationEnterEmailFragmentDirections.actionEnterEmailFragmentToCompleteFragment()
        navigate(action)
    }

    private fun NavController.toEmailAuth() {
        val action =
            OnboardingSchoolAuthorizationEnterEmailFragmentDirections.actionEnterEmailFragmentToEmailAuthFragment()
        navigate(action)
    }
}