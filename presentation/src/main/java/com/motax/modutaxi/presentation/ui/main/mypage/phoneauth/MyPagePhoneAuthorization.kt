package com.motax.modutaxi.presentation.ui.main.mypage.phoneauth

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
import com.motax.modutaxi.presentation.databinding.FragmentMypagePhoneAuthBinding
import com.motax.modutaxi.presentation.ui.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPagePhoneAuthorization :
    BaseFragment<FragmentMypagePhoneAuthBinding>(R.layout.fragment_mypage_phone_auth) {

    private val viewModel: MyPagePhoneAuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.sendAuthCode()
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
                    is MyPagePhoneAuthEvent.NavigateToMyPage -> findNavController().navigateUp()
                    is MyPagePhoneAuthEvent.GoBackToInit -> {
                        val intent = Intent(requireContext(), IntroActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                    is MyPagePhoneAuthEvent.ShowToastMessage -> showToastMessage(it.msg)
                }
            }
        }
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