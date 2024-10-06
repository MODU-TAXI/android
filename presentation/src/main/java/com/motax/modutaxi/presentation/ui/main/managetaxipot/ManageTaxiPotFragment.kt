package com.motax.modutaxi.presentation.ui.main.managetaxipot

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentManageTaxipotBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageTaxiPotFragment :
    BaseFragment<FragmentManageTaxipotBinding>(R.layout.fragment_manage_taxipot) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ManageTaxiPotViewModel by activityViewModels()
    private val args: ManageTaxiPotFragmentArgs by navArgs()
    private val roomId by lazy { args.roomId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.setNotFullScreenMode()
        binding.vm = viewModel
        viewModel.getMemberSource()
        if (roomId != -1L) {
            viewModel.getRoomInfo(roomId)
            binding.btnCreatePot.text = "수정완료"
        }
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ManageTaxiPotEvent.NavigateToArrivalSearch -> findNavController().toArrivalSearch()
                    is ManageTaxiPotEvent.NavigateToDepartureMap -> findNavController().toDepartureMap()
                    is ManageTaxiPotEvent.ShowTimePicker -> showTimePicker(it.hour, it.minute)
                    is ManageTaxiPotEvent.NavigateToMatchDetail -> {
                        findNavController().toMatchDetails(
                            it.id
                        )
                    }

                    is ManageTaxiPotEvent.ShowToast -> showToastMessage(it.msg)
                    is ManageTaxiPotEvent.NavigateBack -> findNavController().navigateUp()
                    is ManageTaxiPotEvent.ShowLoading -> showLoading(requireContext())
                    is ManageTaxiPotEvent.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    private fun showTimePicker(curHour: Int, curMinute: Int) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(curHour)
            .setMinute(curMinute)
            .build()

        picker.addOnPositiveButtonClickListener {
            viewModel.setDepartureTime(picker.hour, picker.minute)
        }

        picker.show(parentFragmentManager, "tag")
    }

    private fun NavController.toMatchDetails(id: Long) {
        val action =
            ManageTaxiPotFragmentDirections.actionManageTaxiPotFragmentToMatchDetailFragment(id)
        navigate(action)
    }

    private fun NavController.toDepartureMap() {
        val action =
            ManageTaxiPotFragmentDirections.actionManageTaxiPotFragmentToDepartureMapFragment()
        navigate(action)
    }

    private fun NavController.toArrivalSearch() {
        val action =
            ManageTaxiPotFragmentDirections.actionManageTaxiPotFragmentToArrivalSearchFragment()
        navigate(action)
    }
}