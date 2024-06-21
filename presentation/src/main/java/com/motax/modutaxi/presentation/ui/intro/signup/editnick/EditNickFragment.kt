package com.motax.modutaxi.presentation.ui.intro.signup.editnick

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentEditNickBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNickFragment: BaseFragment<FragmentEditNickBinding>(R.layout.fragment_edit_nick) {

    private val viewModel: EditNickViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is EditNickEvent.NavigateToHowToKnow -> findNavController().toHowToKnow()
                }
            }
        }
    }

    private fun NavController.toHowToKnow(){
        val action = EditNickFragmentDirections.actionEditNickFragmentToQuestionHowToKnowFragment()
        navigate(action)
    }
}