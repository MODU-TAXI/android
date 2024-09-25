package com.motax.modutaxi.presentation.ui.main.createparty.search

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalSearchBinding
import com.motax.modutaxi.presentation.ui.MtLocation
import com.motax.modutaxi.presentation.ui.checkLocationIsOn
import com.motax.modutaxi.presentation.ui.main.MainActivity
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.departure.DepartureMapViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.search.adapter.AddressSearchResultAdapter
import com.motax.modutaxi.presentation.ui.main.createparty.search.adapter.AllSpotAdapter
import com.motax.modutaxi.presentation.ui.requestLocationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalSearchFragment :
    BaseFragment<FragmentArrivalSearchBinding>(R.layout.fragment_arrival_search) {


    private val viewModel: ArrivalSearchViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    private val locationPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        parentViewModel.setNotFullScreenMode()
        binding.rvSpotList.adapter = AllSpotAdapter()
        binding.rvSpotList.itemAnimator = null
        binding.rvSearchResult.adapter = AddressSearchResultAdapter()
        binding.rvSearchResult.itemAnimator = null
        binding.etSearch.requestFocus()
        showKeyboard(binding.etSearch)
        initEventObserve()
        checkLocationPermission()
        viewModel.getAllSpot()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ArrivalSearchEvent.NavigateToBack -> findNavController().navigateUp()
                    is ArrivalSearchEvent.SelectLocation -> {
                        findNavController().toArrivalMap(
                            it.latitude,
                            it.longitude,
                            it.landMark,
                            it.address,
                            it.isSpot
                        )
                    }
                }
            }
        }
    }

    private fun checkLocationPermission() {
        requireContext().requestLocationPermission(
            locationPermissionList,
            ::startPermissionLauncher,
            ::moveToCurLocation,
        )
    }

    private fun startPermissionLauncher() {
        requestPermissionLauncher.launch(locationPermissionList)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        val isAllGranted = locationPermissionList.all { resultMap[it] == true }
        if (isAllGranted) requireContext().checkLocationIsOn(::moveToCurLocation)
    }

    private fun moveToCurLocation(state: Boolean) {
        if (state) {
            LocationServices.getFusedLocationProviderClient(activity as MainActivity).apply {
                lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        viewModel.setCurLocation(it.latitude, it.longitude)
                    }
                }
            }
        }
    }

    private fun showKeyboard(view: EditText) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun NavController.toArrivalMap(
        latitude: Double,
        longitude: Double,
        landMark: String,
        address: String,
        isSpot: Boolean
    ) {
        val action =
            ArrivalSearchFragmentDirections.actionArrivalSearchFragmentToArrivalMapFragment(
                MtLocation(latitude, longitude, landMark, address, isSpot)
            )
        navigate(action)
    }
}