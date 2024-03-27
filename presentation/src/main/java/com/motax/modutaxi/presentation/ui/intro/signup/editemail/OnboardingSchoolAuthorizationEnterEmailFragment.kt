package com.motax.modutaxi.presentation.ui.intro.signup.editemail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        requestFocusAndShowKeyboard()
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

    private fun requestFocusAndShowKeyboard() {
        binding.etAuthorizationCode.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(binding.etAuthorizationCode, InputMethodManager.SHOW_IMPLICIT)
    }
}