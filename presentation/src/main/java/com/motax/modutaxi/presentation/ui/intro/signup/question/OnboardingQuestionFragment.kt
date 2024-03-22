package com.motax.modutaxi.presentation.ui.intro.signup.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentOnboardingQuestionHowToKnowAppBinding

class OnboardingQuestionFragment :
    BaseFragment<FragmentOnboardingQuestionHowToKnowAppBinding>(R.layout.fragment_onboarding_question_how_to_know_app) {

    private val viewModel : OnboardingQuestionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setBtnListener()
        initStateObserve()
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toSchoolAuthorizationEnterEmail()
        }

    }

    private fun initStateObserve(){
        repeatOnStarted {
            viewModel.uiState.collect{
                if(it.everyTimeSelected || it.byEtcSelected || it.directSearchSelected || it.friendlyRecommendationSelected){
                    binding.btnCheck.setBackgroundResource(R.drawable.rect_black0_fill_nostroke_61radius)
                    binding.btnCheck.isEnabled = true
                } else {
                    binding.btnCheck.setBackgroundResource(R.drawable.rect_grey1_fill_nostroke_61radius)
                    binding.btnCheck.isEnabled = false
                }
            }
        }
    }

    private fun NavController.toSchoolAuthorizationEnterEmail() {
        val action =
            OnboardingQuestionFragmentDirections.actionQuestionFragmentToSchoolAuthorizationEnterEmail()
        navigate(action)
    }
}