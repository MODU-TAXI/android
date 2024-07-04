package com.motax.modutaxi.presentation.ui.main.mypage.emailauth

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
import com.motax.modutaxi.presentation.databinding.FragmentMypageSchoolAuthorizationEnterCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageSchoolAuthorizationEnterCodeFragment :
    BaseFragment<FragmentMypageSchoolAuthorizationEnterCodeBinding>(
        R.layout.fragment_mypage_school_authorization_enter_code
    ) {

    private val viewModel: MyPageSchoolAuthorizationEnterCodeViewModel by viewModels()

    private val args: MyPageSchoolAuthorizationEnterCodeFragmentArgs by navArgs()
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
                    is MyPageEmailAuthEvent.NavigateToComplete -> findNavController().toMyPageComplete()
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

    private fun NavController.toMyPageComplete() {
        val action =
            MyPageSchoolAuthorizationEnterCodeFragmentDirections.actionMyPageEmailAuthFragmentToMyPageFragment()
        navigate(action)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}