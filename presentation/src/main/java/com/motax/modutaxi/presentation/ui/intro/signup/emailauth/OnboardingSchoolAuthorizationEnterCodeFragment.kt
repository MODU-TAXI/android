package com.motax.modutaxi.presentation.ui.intro.signup.emailauth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingSchoolAuthorizationEnterCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSchoolAuthorizationEnterCodeFragment :
    BaseFragment<FragmentOnboardingSchoolAuthorizationEnterCodeBinding>(
        R.layout.fragment_onboarding_school_authorization_enter_code
    ) {

    private val viewModel: OnboardingSchoolAuthorizationEnterCodeViewModel by viewModels()

    private val args: OnboardingSchoolAuthorizationEnterCodeFragmentArgs by navArgs()
    private val email by lazy { args.email }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setEmailData(email)
        initEventObserve()

        requestFocusAndShowKeyboard()
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is EmailAuthEvent.NavigateToComplete -> findNavController().toOnboardingComplete()
                }
            }
        }
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etAuthorizationCode.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(
            binding.etAuthorizationCode, InputMethodManager.SHOW_IMPLICIT
        )
    }

    private fun NavController.toOnboardingComplete() {
        val action =
            OnboardingSchoolAuthorizationEnterCodeFragmentDirections.actionEmailAuthFragmentToCompleteFragment()
        navigate(action)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}