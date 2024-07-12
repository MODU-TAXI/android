package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.complete

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentWithdrawalCompleteBinding
import com.motax.modutaxi.presentation.ui.intro.IntroActivity
import com.motax.modutaxi.presentation.ui.main.MainActivity
import com.motax.modutaxi.presentation.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteFragment :BaseFragment<FragmentWithdrawalCompleteBinding>(R.layout.fragment_withdrawal_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheck.setOnClickListener {
            navigateToMainScreen()
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
}