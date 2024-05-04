package com.motax.modutaxi.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is HomeEvent.NavigateToShowParty -> findNavController().toShowParty()
                    is HomeEvent.NavigateToCreateParty -> findNavController().toCreateParty()
                }
            }
        }
    }

    private fun NavController.toShowParty() {
        val action = HomeFragmentDirections.actionHomeFragmentToShowPartyFragment()
        navigate(action)
    }

    private fun NavController.toCreateParty() {
        val action = HomeFragmentDirections.actionHomeFragmentToCreatePartyFragment()
        navigate(action)
    }

}