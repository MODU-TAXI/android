package com.motax.modutaxi.presentation.ui.main.mypage.editnick

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMypageEditNickBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageEditNickFragment: BaseFragment<FragmentMypageEditNickBinding>(R.layout.fragment_mypage_edit_nick) {

    private val viewModel: MyPageEditNickViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is MyPageEditNickEvent.NavigateToMyPage -> findNavController().toMyPage()
                }
            }
        }
    }

    private fun NavController.toMyPage(){
        val action = MyPageEditNickFragmentDirections.actionEditNickToMypage()
        navigate(action)
    }
}