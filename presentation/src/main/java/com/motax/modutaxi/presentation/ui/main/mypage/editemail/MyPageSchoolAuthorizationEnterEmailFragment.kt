package com.motax.modutaxi.presentation.ui.main.mypage.editemail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMypageSchoolAuthorizationEnterEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageSchoolAuthorizationEnterEmailFragment :
    BaseFragment<FragmentMypageSchoolAuthorizationEnterEmailBinding>(
        R.layout.fragment_mypage_school_authorization_enter_email
    ) {

    private val viewModel: MyPageSchoolAuthorizationEnterEmailViewModel by viewModels()

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
                    is MyPageSchoolAuthorizationEnterEmailEvent.NavigateToMyPageComplete -> findNavController().toMyPageComplete()
                    is MyPageSchoolAuthorizationEnterEmailEvent.NavigateToEnterCode -> findNavController().toEmailAuth(it.email)
                    is MyPageSchoolAuthorizationEnterEmailEvent.ShowToastMessage -> showToastMessage(
                        it.msg
                    )
                }
            }
        }
    }

    private fun NavController.toMyPageComplete() {
        val action =
            MyPageSchoolAuthorizationEnterEmailFragmentDirections.actionEmailAuthFragmentToMyPageFragment()
        navigate(action)
    }

    private fun NavController.toEmailAuth(email : String) {
        val action =
            MyPageSchoolAuthorizationEnterEmailFragmentDirections.actionEmailAuthFragmentToEmailAuthFragment(email)
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