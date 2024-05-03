package com.motax.modutaxi.presentation.ui.intro.signup.complete

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingCompleteBinding
import com.motax.modutaxi.presentation.ui.main.MainActivity

class OnboardingCompleteFragment :
    BaseFragment<FragmentOnboardingCompleteBinding>(R.layout.fragment_onboarding_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSchoolAuthorizationCheck.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}