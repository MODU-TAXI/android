package com.motax.modutaxi.presentation.ui.intro.signup.identification

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingIdentificationBinding
import kotlinx.coroutines.launch

class OnboardingIdentificationFragment :
    BaseFragment<FragmentOnboardingIdentificationBinding>(R.layout.fragment_onboarding_identification) {

    private val viewModel: OnboardingIdentificationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setCheckBtnListener()
        setListeners()
        setTextChangeListener()
        observeFocusedFieldWithLifeCycleScope()
        requestFocusAndShowKeyboard()

        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun setTextChangeListener() {
        binding.etPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etName.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(binding.etName, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setListeners() {
        binding.etName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.focusOnName()
        }

        binding.etPhoneNumber.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.focusOnPhoneNumber()
        }

        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            handleGenderSelection(checkedId)
            viewModel.focusOnGender()
        }
    }

    private fun handleGenderSelection(checkedId: Int) {
        when (checkedId) {
            R.id.radioButtonMale -> viewModel.updateGender("Male")
            R.id.radioButtonFemale -> viewModel.updateGender("Female")
        }
    }


    private fun setCheckBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toPhoneAuth()
        }
    }

    private fun NavController.toPhoneAuth() {
        val action =
            OnboardingIdentificationFragmentDirections.actionIdentificationFragmentToPhoneAuthFragment()
        navigate(action)
    }

    private fun observeFocusedFieldWithLifeCycleScope() {//TODO 추후 셀렉터로 변경
        lifecycleScope.launch {
            viewModel.focusedField.collect() { it ->
                when (it) {
                    OnboardingIdentificationViewModel.FocusedField.NAME -> {
                        binding.layoutName.setBackgroundResource(R.drawable.rect_nofill_blackstroke_12radius)
                        binding.layoutPhoneNumber.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                        binding.layoutGender.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                    }

                    OnboardingIdentificationViewModel.FocusedField.PHONE -> {
                        binding.layoutPhoneNumber.setBackgroundResource(R.drawable.rect_nofill_blackstroke_12radius)
                        binding.layoutName.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                        binding.layoutGender.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)

                    }

                    OnboardingIdentificationViewModel.FocusedField.GENDER -> {
                        binding.layoutGender.setBackgroundResource(R.drawable.rect_nofill_blackstroke_12radius)
                        binding.layoutName.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                        binding.layoutPhoneNumber.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)

                    }

                    else -> {
                        binding.layoutName.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                        binding.layoutPhoneNumber.setBackgroundResource(R.drawable.rect_grey0_fill_nostroke_12radius)
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}