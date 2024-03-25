package com.motax.modutaxi.presentation.ui.intro.signup.phoneauth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingPhoneAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingPhoneAuthorization: BaseFragment<FragmentOnboardingPhoneAuthorizationBinding>(R.layout.fragment_onboarding_phone_authorization) {

    private val viewModel : OnboardingPhoneAuthorizationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
    }
}