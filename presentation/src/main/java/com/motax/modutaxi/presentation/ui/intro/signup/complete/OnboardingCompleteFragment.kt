package com.motax.modutaxi.presentation.ui.intro.signup.complete

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingCompleteBinding

class OnboardingCompleteFragment :
    BaseFragment<FragmentOnboardingCompleteBinding>(R.layout.fragment_onboarding_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSchoolAuthorizationCheck.setOnClickListener {
            findNavController().navigate(R.id.action_completeFragment_to_taxipotListFragment)
        }
    }
}