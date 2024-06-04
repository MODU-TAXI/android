package com.motax.modutaxi.presentation.ui.main.showparty

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentShowPartyBinding
import com.motax.modutaxi.presentation.ui.checkLocationIsOn
import com.motax.modutaxi.presentation.ui.main.MainActivity
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.requestLocationPermission
import com.motax.modutaxi.presentation.ui.to8Round
import com.motax.modutaxi.presentation.ui.toMatchDetail
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowPartyFragment : BaseFragment<FragmentShowPartyBinding>(R.layout.fragment_show_party),
    OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

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
        initMapView()
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

                    }

                    is ShowPartyEvent.NavigateToSearch -> findNavController().toShowPartySearch()
                    is ShowPartyEvent.NavigateToCreateParty -> findNavController().toCreateParty()
                    is ShowPartyEvent.NavigateToMatchDetail -> findNavController().toMatchDetail(it.id)
                }
            }
        }
    }

    private fun initMapView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.showparty_map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.showparty_map_fragment, it)
                        .commit()
                }

        mapFragment.getMapAsync(this)
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
        // todo 화면 이동시 리스너

        naverMap.addOnCameraChangeListener { _, isStop ->
            if (!isStop) {
                viewModel.changeMovingState(true)
            }
        }

        naverMap.addOnCameraIdleListener {
            viewModel.changeMovingState(false)
            val cameraPosition = naverMap.cameraPosition.target
            if(viewModel.uiState.value.latitude != cameraPosition.latitude.to8Round() || viewModel.uiState.value.longitude != cameraPosition.longitude.to8Round()){
                // todo 주변 방 호출 API
            }
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

    private fun NavController.toShowPartySearch(){
        val action = ShowPartyFragmentDirections.actionShowPartyFragmentToShowPartySearchFragment()
        navigate(action)
    }

    private fun NavController.toCreateParty(){
        val action = ShowPartyFragmentDirections.actionShowPartyFragmentToCreatePartyFragment()
        navigate(action)
    }


}