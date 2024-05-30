package com.motax.modutaxi.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentHomeBinding
import com.motax.modutaxi.presentation.ui.main.home.adapter.RealtimeTaxiPotAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        val adapter = RealtimeTaxiPotAdapter()
        binding.rvRealtimeTaxipotList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                adapter.submitList(uiState.realtimeTaxiPotList)
            }
        }
        setupObservers()
        initEventObserve()
        //viewModel.getRealtimeTaxiPots()
    }
    private fun setupObservers() {
        viewModel.isParticipating.observe(viewLifecycleOwner, Observer { isParticipating ->
            val themeId = if (isParticipating) {
                R.style.Theme_Modutaxi
            } else {
                R.style.Theme_Modutaxi_NotParticipating
            }
            activity?.setTheme(themeId)
            updateStatusBarColor(isParticipating)
        })
    }

    private fun updateStatusBarColor(isParticipating: Boolean) {
        val statusBarColor = if (isParticipating) {
            R.color.mx_sub500
        } else {
            R.color.gray_4F4F4F
        }
        activity?.window?.statusBarColor = resources.getColor(statusBarColor, null)
    }
    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is HomeEvent.NavigateToShowParty -> findNavController().toShowParty()
                    is HomeEvent.NavigateToCreateParty -> findNavController().toCreateParty()
                    is HomeEvent.NavigateToSearch -> {}
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