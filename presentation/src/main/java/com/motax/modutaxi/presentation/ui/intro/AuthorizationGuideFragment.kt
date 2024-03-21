package com.motax.modutaxi.presentation.ui.intro

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAuthorizationGuideBinding

class AuthorizationGuideFragment :
    BaseFragment<FragmentAuthorizationGuideBinding>(R.layout.fragment_authorization_guide) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBtnListener();
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled
            findNavController().toIdentification()
        }
    }

    private fun NavController.toIdentification() {
        val action =
            AuthorizationGuideFragmentDirections.actionAuthorizationGuideFragmentToIdentificationFragment()
        navigate(action)
    }
}