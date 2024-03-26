package com.motax.modutaxi.presentation.ui.intro.signup.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingQuestionWhenWantTaxiBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingQuestionWhenWantTaxiFragment : BaseFragment<FragmentOnboardingQuestionWhenWantTaxiBinding>(
    R.layout.fragment_onboarding_question_when_want_taxi){


    private val viewModel: OnboardingQuestionWhenWantTaxiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setBtnListener()
        initStateObserve()
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            findNavController().toQuestionWhenWantTaxi()
        }
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiState.collect {
                binding.btnCheck.isEnabled =
                    it.whenLateSelected || it.byEtcSelected || it.directSearchSelected || it.whenBusLineTooLongSelected
            }
        }
    }

    private fun NavController.toQuestionWhenWantTaxi() {
        val action = OnboardingQuestionWhenWantTaxiFragmentDirections.actionQuestionWhenWantTaxiFragmentToEnterEmailFragment()
        navigate(action)
    }
}