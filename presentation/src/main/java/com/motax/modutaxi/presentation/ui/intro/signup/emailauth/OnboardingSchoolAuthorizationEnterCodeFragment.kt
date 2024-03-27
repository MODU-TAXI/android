package com.motax.modutaxi.presentation.ui.intro.signup.emailauth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        requestFocusAndShowKeyboard()
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etAuthorizationCode.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(binding.etAuthorizationCode, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setBtnListener() {
        binding.btnSchoolAuthorizationCheck.setOnClickListener {
            binding.btnSchoolAuthorizationCheck.isEnabled
            findNavController().toOnboardingComplete()
        }
    }

    private fun NavController.toOnboardingComplete() {
        val action =
            OnboardingSchoolAuthorizationEnterCodeFragmentDirections.actionEmailAuthFragmentToCompleteFragment()
        navigate(action)
    }


}