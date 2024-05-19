package com.motax.modutaxi.presentation.ui.main.createparty.map

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMapBinding
import com.motax.modutaxi.presentation.ui.checkLocationIsOn
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.CreatePartyViewModel
import com.motax.modutaxi.presentation.ui.requestLocationPermission
import com.motax.modutaxi.presentation.util.Constants.TAG
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val pathList = mutableListOf<PathOverlay>()

    private val locationPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: MapViewModel by activityViewModels()
    private val createPartyViewModel: CreatePartyViewModel by activityViewModels()

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

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
                    is MapEvent.NavigateToSearch -> findNavController().toAddressSearch()
                    is MapEvent.SelectDeparture -> {
                        createPartyViewModel.setDepartureInfo(
                            it.longitude,
                            it.latitude,
                            it.name.ifBlank { it.address }
                        )
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initMapView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onMapReady(nM: NaverMap) {
        this.naverMap = nM
        with(naverMap.uiSettings) {
            isCompassEnabled = false
            isZoomControlEnabled = false
        }
        naverMap.locationSource = locationSource
        initStateObserve()
        setMapListener()
        setInitCamera()
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.trackingState.collect {
                when (it) {
                    is TrackingState.TryOn -> {
                        Log.d(TAG,"tryon")
                        requireContext().requestLocationPermission(
                            locationPermissionList,
                            ::startPermissionLauncher,
                            ::onTrackingChangeListener,
                        )
                    }

                    is TrackingState.On -> {
                        naverMap.locationTrackingMode =
                            LocationTrackingMode.Follow
                    }

                    is TrackingState.Off -> naverMap.locationTrackingMode =
                        LocationTrackingMode.None
                }
            }
        }
    }
    private fun startPermissionLauncher() {
        requestPermissionLauncher.launch(locationPermissionList)
    }

    private fun onTrackingChangeListener(state: Boolean) {
        if (state) viewModel.trackingOn()
        else viewModel.trackingOff()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        val isAllGranted = locationPermissionList.all { resultMap[it] == true }
        if (isAllGranted) requireContext().checkLocationIsOn(::onTrackingChangeListener)
        else viewModel.trackingOff()
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
            viewModel.getAddressFromGeo(
                cameraPosition.latitude,
                cameraPosition.longitude
            )
        }
    }

    private fun setInitCamera() {
        val locate = CameraUpdate.scrollTo(
            LatLng(
                viewModel.uiState.value.latitude,
                viewModel.uiState.value.longitude
            )
        )
        naverMap.moveCamera(locate)
    }


//    private fun setPath() {
//        val path = PathOverlay()
//        val manager = resources.assets
//        val inputStream = manager.open("test.json")
//        val jsonString = inputStream.bufferedReader().use { it.readText() }
//
//        val gson = Gson()
//        val data: TestMapData = gson.fromJson(jsonString, TestMapData::class.java)
//
//        val list = data.route.traoptimal[0].path.map {
//            LatLng(it[1], it[0])
//        }
//
//        path.coords = list
//
//        path.color = Color.RED
//        path.map = naverMap
//    }

    private fun NavController.toAddressSearch() {
        val action = MapFragmentDirections.actionMapFragmentToAddressSearchFragment()
        navigate(action)
    }


}