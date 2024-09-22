package com.motax.modutaxi.presentation.ui.main.showparty

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentShowPartyBinding
import com.motax.modutaxi.presentation.ui.checkLocationIsOn
import com.motax.modutaxi.presentation.ui.main.MainActivity
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.showparty.bottomsheet.TaxiPotListBottomSheetFragment
import com.motax.modutaxi.presentation.ui.main.showparty.model.UiTaxiPotListItem
import com.motax.modutaxi.presentation.ui.requestLocationPermission
import com.motax.modutaxi.presentation.ui.toMatchDetail
import com.motax.modutaxi.presentation.util.Constants.TAG
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowPartyFragment : BaseFragment<FragmentShowPartyBinding>(R.layout.fragment_show_party),
    OnMapReadyCallback {


    companion object {
        const val NEW = 0
        const val NEXT_PAGE = 1
    }

    private lateinit var naverMap: NaverMap
    private val markerList = mutableListOf<Marker>()
    private var selectedMarker: Marker? = null
    private var taxiPotListBottomSheetFragment: TaxiPotListBottomSheetFragment? = null
    private var lastButtonMargin = 0

    private val locationPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ShowPartyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        parentViewModel.setFullScreenMode()
        initEventObserve()
        initStateObserve()
        initMapView()
        initBottomSheet()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ShowPartyEvent.MoveToCurLocation -> {
                        requireContext().requestLocationPermission(
                            locationPermissionList,
                            ::startPermissionLauncher,
                            ::moveToCurLocation
                        )
                    }

                    is ShowPartyEvent.SetMarkers -> {

                        removeMarkers()
                        it.list.forEach { data ->
                            setMarker(data)
                        }
                    }

                    is ShowPartyEvent.NavigateToSearch -> findNavController().toShowPartySearch()
                    is ShowPartyEvent.NavigateToCreateParty -> findNavController().toCreateParty()
                    is ShowPartyEvent.NavigateToMatchDetail -> findNavController().toMatchDetail(it.id)
                    is ShowPartyEvent.ShowSpotFilterSheet -> findNavController().toSelectSpotFilter()
                }
            }
        }
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.bottomSheetHeight.collect {
                lastButtonMargin = 500 + (it * 1390).toInt()
                setButtonsMargin(lastButtonMargin)
            }
        }

        repeatOnStarted {
            viewModel.bottomSheetUiState.collect {
                if (it.showBottomSheet) {
                    selectedMarker?.let {
                        binding.partyMarker.text = viewModel.uiState.value.selectedTaxiPotData.arrivalName
                        selectedMarker?.icon = OverlayImage.fromView(binding.partyMarker)
                    }
                    setButtonsMargin(lastButtonMargin)
                } else {
                    setButtonsMargin(0)
                }
            }
        }
    }

    private fun setButtonsMargin(margin: Int) {
        val layoutParams = binding.btnCreateTaxiPot.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, margin)
        binding.btnCreateTaxiPot.layoutParams = layoutParams
    }

    private fun initMapView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.showparty_map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.showparty_map_fragment, it)
                        .commit()
                }

        mapFragment.getMapAsync(this)
        binding.btnRefresh.setOnClickListener {
            viewModel.getTaxiPotData()
        }
    }

    private fun initBottomSheet() {
        if (childFragmentManager.findFragmentById(R.id.show_party_bottom_sheet) == null) {
            taxiPotListBottomSheetFragment = TaxiPotListBottomSheetFragment()
            childFragmentManager.beginTransaction().add(
                R.id.show_party_bottom_sheet,
                taxiPotListBottomSheetFragment!!
            ).commit()
        }
    }

    override fun onMapReady(nM: NaverMap) {
        this.naverMap = nM
        with(naverMap.uiSettings) {
            isCompassEnabled = false
            isZoomControlEnabled = false
        }
        setMapListener()
        setInitCamera()
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
                        moveCamera(it.latitude, it.longitude)
                    }
                }
            }
        }
    }

    private fun setMapListener() {

        naverMap.addOnCameraChangeListener { i, b ->
            viewModel.setZoomLevel(naverMap.cameraPosition.zoom)
        }
        naverMap.addOnCameraIdleListener {
            val cameraPosition = naverMap.cameraPosition.target
            viewModel.getTaxiPotData(cameraPosition.latitude, cameraPosition.longitude)
        }
    }

    private fun setInitCamera() {
        if (viewModel.uiState.value.isFromSearch) {
            moveCamera(viewModel.uiState.value.latitude, viewModel.uiState.value.longitude)
        } else {
            viewModel.locationBtnClicked()
        }
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        val locate = CameraUpdate.scrollTo(
            LatLng(latitude, longitude)
        )
        naverMap.moveCamera(locate)
    }

    private fun removeMarkers(){
        markerList.forEach {
            it.map = null
        }

        markerList.clear()
    }


    private fun setMarker(data: UiTaxiPotListItem) {
        Log.d(TAG,data.arrivalName)
        val marker = Marker()
        binding.partyMarker.text = data.arrivalName
        marker.position = LatLng(data.departureLatitude, data.departureLongitude)
        marker.icon = OverlayImage.fromView(binding.partyMarker)
        marker.setOnClickListener {
            selectedMarker?.let {
                binding.partyMarker.text = viewModel.uiState.value.selectedTaxiPotData.arrivalName
                selectedMarker?.icon = OverlayImage.fromView(binding.partyMarker)
            }
            binding.partyMarkerSelected.text = data.arrivalName
            marker.icon = OverlayImage.fromView(binding.partyMarkerSelected)
            selectedMarker = marker
            viewModel.selectMarker(data)
            true
        }
        marker.map = naverMap
        markerList.add(marker)
    }

    private fun NavController.toShowPartySearch() {
        val action = ShowPartyFragmentDirections.actionShowPartyFragmentToShowPartySearchFragment()
        navigate(action)
    }

    private fun NavController.toCreateParty() {
        val action = ShowPartyFragmentDirections.actionShowPartyFragmentToCreatePartyFragment()
        navigate(action)
    }

    private fun NavController.toSelectSpotFilter(){
        val action = ShowPartyFragmentDirections.actionShowPartyFragmentToSelectSpotFilterBottomSheetFragment()
        navigate(action)
    }
}