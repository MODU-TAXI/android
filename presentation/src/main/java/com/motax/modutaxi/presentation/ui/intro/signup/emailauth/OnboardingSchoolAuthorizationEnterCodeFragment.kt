package com.motax.modutaxi.presentation.ui.intro.signup.emailauth

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingSchoolAuthorizationEnterCodeBinding

class OnboardingSchoolAuthorizationEnterCodeFragment :
    BaseFragment<FragmentOnboardingSchoolAuthorizationEnterCodeBinding>(
        R.layout.fragment_onboarding_school_authorization_enter_code
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener()
    }

    private fun setBtnListener() {
        binding.btnSchoolAuthorizationCheck.setOnClickListener {
            binding.btnSchoolAuthorizationCheck.isEnabled
            findNavController().toOnboardingComplete()
        }
    }

    private fun NavController.toOnboardingComplete() {
        val action =
            OnboardingSchoolAuthorizationEnterCodeFragmentDirections.actionEnterCodeFragmentToOnboardingCompleteFragment()
        navigate(action)
    }
}