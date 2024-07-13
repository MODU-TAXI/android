package com.motax.modutaxi.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentHomeBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.home.adapter.RealtimeTaxiPotAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    private var adapter: RealtimeTaxiPotAdapter? = null
//    private lateinit var adapters: RealtimeTaxiPotAdapter // 가급적 안쓰는게 좋음
//    // 앱이 터지는거는 무조건 기피 해야함
//    private val adapters2 by lazy { RealtimeTaxiPotAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.setFullScreenMode()
        binding.vm = viewModel
        adapter = RealtimeTaxiPotAdapter()
        binding.rvRealtimeTaxipotList.adapter = adapter


        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is HomeEvent.NavigateToShowParty -> findNavController().toShowParty()
                    is HomeEvent.NavigateToCreateParty -> findNavController().toCreateParty()
                    is HomeEvent.NavigateToMatchDetail -> findNavController().toMatchDetail(it.id)
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

    private fun NavController.toMatchDetail(roomId: Long) {
        val action = HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(roomId)
        navigate(action)
    }
}