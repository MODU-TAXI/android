package com.motax.modutaxi.presentation.ui.intro.signup.identification

import android.os.Bundle
import android.view.View
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
        setCheckBtnListener()
        setRadioGroupListener()
    }

    private fun setRadioGroupListener() {
        binding.rgGender.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId) {
                R.id.radioButtonMale -> viewModel.updateGender("Male")
                R.id.radioButtonFemale -> viewModel.updateGender("Female")
            }
        }
    }
    private fun setCheckBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toQuestion()
        }
    }

    private fun NavController.toQuestion() {
        val action =
            OnboardingIdentificationFragmentDirections.actionOnboardingIdentificationFragmentToOnboardingPhoneAuthorization2()
        navigate(action)
    }
}