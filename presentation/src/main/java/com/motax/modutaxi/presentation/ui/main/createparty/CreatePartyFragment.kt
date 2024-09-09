package com.motax.modutaxi.presentation.ui.main.createparty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCreatePartyBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.toMatchDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePartyFragment :
    BaseFragment<FragmentCreatePartyBinding>(R.layout.fragment_create_party) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: CreatePartyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        parentViewModel.setNotFullScreenMode()
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is CreatePartyEvent.NavigateToArrivalSearch -> findNavController().toArrivalSearch()
                    is CreatePartyEvent.NavigateToDepartureMap -> findNavController().toDepartureMap()
                    is CreatePartyEvent.ShowTimePicker -> showTimePicker(it.hour, it.minute)
                    is CreatePartyEvent.NavigateToMatchDetail -> findNavController().toMatchDetails(
                        it.id
                    )

                    is CreatePartyEvent.ShowToast -> showToastMessage(it.msg)
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

    private fun NavController.toMatchDetails(id: Long){
        val action = CreatePartyFragmentDirections.actionCreatePartyFragmentToMatchDetailFragment(id)
        navigate(action)
    }

    private fun NavController.toDepartureMap() {
        val action = CreatePartyFragmentDirections.actionCreatePartyFragmentToDepartureMapFragment()
        navigate(action)
    }

    private fun NavController.toArrivalSearch() {
        val action =
            CreatePartyFragmentDirections.actionCreatePartyFragmentToArrivalSearchFragment()
        navigate(action)
    }
}