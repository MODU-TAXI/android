package com.motax.modutaxi.presentation.ui.intro.login.sociallogin

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBtnListener()
    }

    private fun setBtnListener() {
        binding.btnLoginKakao.setOnClickListener {
            binding.btnLoginKakao.isEnabled
            findNavController().toLogin()
        }
    }

    private fun NavController.toLogin() {
        val action = LoginFragmentDirections.actionLoginFragmentToOnboardFragment()
        navigate(action)
    }

}