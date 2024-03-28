package com.motax.modutaxi.presentation.ui.intro.signup.phoneauth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingPhoneAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingPhoneAuthorization :
    BaseFragment<FragmentOnboardingPhoneAuthBinding>(R.layout.fragment_onboarding_phone_auth) {

    private val viewModel: OnboardingPhoneAuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        requestFocusAndShowKeyboard()

        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it) {
                    is PhoneAuthEvent.NavigateToQuestionHowToKnow -> findNavController().toQuestionHowToKnow()
                }
            }
        }
    }

    private fun NavController.toQuestionHowToKnow(){
        val action = OnboardingPhoneAuthorizationDirections.actionPhoneAuthFragmentToQuestionHowToKnowFragment()
        navigate(action)
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etAuthorizationCode.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(binding.etAuthorizationCode, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}