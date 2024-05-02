package com.motax.modutaxi.presentation.ui.intro.signup.editemail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingSchoolAuthorizationEnterEmailBinding
import com.motax.modutaxi.presentation.ui.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSchoolAuthorizationEnterEmailFragment :
    BaseFragment<FragmentOnboardingSchoolAuthorizationEnterEmailBinding>(
        R.layout.fragment_onboarding_school_authorization_enter_email
    ) {

    private val viewModel: OnboardingSchoolAuthorizationEnterEmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
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
                    is OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToOnboardingComplete -> findNavController().toOnboardingComplete()
                    is OnboardingSchoolAuthorizationEnterEmailEvent.NavigateToEnterCode -> findNavController().toEmailAuth(it.email)
                    is OnboardingSchoolAuthorizationEnterEmailEvent.ShowToastMessage -> showToastMessage(
                        it.msg
                    )

                    is OnboardingSchoolAuthorizationEnterEmailEvent.GoBackToInit -> {
                        val intent = Intent(requireContext(), IntroActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun NavController.toOnboardingComplete() {
        val action =
            OnboardingSchoolAuthorizationEnterEmailFragmentDirections.actionEnterEmailFragmentToCompleteFragment()
        navigate(action)
    }

    private fun NavController.toEmailAuth(email : String) {
        val action =
            OnboardingSchoolAuthorizationEnterEmailFragmentDirections.actionEnterEmailFragmentToEmailAuthFragment(email)
        navigate(action)
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etAuthorizationCode.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(
            binding.etAuthorizationCode,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}