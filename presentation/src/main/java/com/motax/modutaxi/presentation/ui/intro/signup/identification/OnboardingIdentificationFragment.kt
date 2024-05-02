package com.motax.modutaxi.presentation.ui.intro.signup.identification

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingIdentificationBinding

class OnboardingIdentificationFragment :
    BaseFragment<FragmentOnboardingIdentificationBinding>(R.layout.fragment_onboarding_identification) {

    private val viewModel: OnboardingIdentificationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initStateObserve()
        initEventObserve()
        setListeners()
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiState.collect {
                when (it.focusedField) {
                    FocusedField.NAME -> {
                        binding.etName.requestFocus()
                        showKeyboard(binding.etName)
                    }

                    FocusedField.PHONE -> {
                        binding.etPhoneNumber.requestFocus()
                        showKeyboard(binding.etPhoneNumber)
                    }

                    else -> {
                        binding.etName.clearFocus()
                        binding.etPhoneNumber.clearFocus()
                        hideKeyboard()
                    }
                }
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is OnboardingIdentificationEvent.NavigateToPhoneAuth -> findNavController().toPhoneAuth()
                }
            }
        }
    }

    private fun setListeners() {

        binding.etPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonMale -> viewModel.updateGender("Male")
                R.id.radioButtonFemale -> viewModel.updateGender("Female")
            }
        }

        binding.etName.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) viewModel.focusOnName()
        }

        binding.etPhoneNumber.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) viewModel.focusOnPhoneNumber()
        }
    }

    private fun NavController.toPhoneAuth() {
        val action =
            OnboardingIdentificationFragmentDirections.actionIdentificationFragmentToPhoneAuthFragment()
        navigate(action)
    }

    private fun showKeyboard(view: EditText) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}