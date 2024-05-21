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
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.LocationServices
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAddressSearchBinding
import com.motax.modutaxi.presentation.ui.checkLocationIsOn
import com.motax.modutaxi.presentation.ui.main.MainActivity
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.departuremap.DepartureMapViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.search.adapter.AddressSearchResultAdapter
import com.motax.modutaxi.presentation.ui.requestLocationPermission
import com.motax.modutaxi.presentation.util.Constants.ARRIVAL_SEARCH
import com.motax.modutaxi.presentation.util.Constants.DEPARTURE_SEARCH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressSearchFragment :
    BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {

    private val viewModel: AddressSearchViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val departureMapViewModel: DepartureMapViewModel by activityViewModels()

    private val locationPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val args: AddressSearchFragmentArgs by navArgs()
    private val type by lazy { args.type }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel


        parentViewModel.setNotFullScreenMode()
        binding.rvSearchResult.adapter = AddressSearchResultAdapter()
        binding.rvSearchResult.itemAnimator = null
        binding.etSearch.requestFocus()
        showKeyboard(binding.etSearch)
        initEventObserve()
        checkLocationPermission()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is AddressSearchEvent.NavigateToBack -> findNavController().navigateUp()
                    is AddressSearchEvent.SelectLocation -> {
                        when (type) {
                            DEPARTURE_SEARCH -> {
                                findNavController().navigateUp()
                                departureMapViewModel.selectLocationFromSearch(
                                    it.latitude,
                                    it.longitude,
                                    it.landMark
                                )
                            }

                            ARRIVAL_SEARCH -> {
                                findNavController().toArrivalMap()
                            }
                        }
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

    private fun NavController.toArrivalMap() {
        val action =
            AddressSearchFragmentDirections.actionAddressSearchFragmentToArrivalMapFragment()
        navigate(action)
    }
}