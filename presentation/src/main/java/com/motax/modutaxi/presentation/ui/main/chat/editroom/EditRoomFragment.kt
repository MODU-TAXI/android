package com.motax.modutaxi.presentation.ui.main.chat.editroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentEditRoomBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.CreatePartyFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRoomFragment : BaseFragment<FragmentEditRoomBinding>(R.layout.fragment_edit_room) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: EditRoomViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        parentViewModel.setNotFullScreenMode()
        initEventObserve()
        binding.btnCreatePot.setOnClickListener {
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is EditRoomEvent.NavigateToArrivalSearch -> findNavController().toArrivalSearch()
                    is EditRoomEvent.NavigateToDepartureMap -> findNavController().toDepartureMap()
                    is EditRoomEvent.ShowTimePicker -> showTimePicker(it.hour, it.minute)
                    is EditRoomEvent.NavigateToMatchDetail -> findNavController().toMatchDetails(
                        it.id
                    )

                    is EditRoomEvent.ShowToast -> showToastMessage(it.msg)
                    is EditRoomEvent.NavigateBack -> findNavController().navigateUp()
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