package com.motax.modutaxi.presentation.ui.main.createparty.arrival

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalMapBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.CreatePartyViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.model.UiMarkerItem
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
class ArrivalMapFragment : BaseFragment<FragmentArrivalMapBinding>(R.layout.fragment_arrival_map),
    OnMapReadyCallback {

    private val markerList = mutableListOf<Marker>()
    private val selectedMarker: Marker? = null

    private lateinit var naverMap: NaverMap
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ArrivalMapViewModel by activityViewModels()
    private val createPartyViewModel: CreatePartyViewModel by activityViewModels()
    private val args: ArrivalMapFragmentArgs by navArgs()

    private val selectedLocation by lazy { args.selectLocation }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setSearchKeyWord(selectedLocation.landMark.toString())
        initEventObserve()
        parentViewModel.setFullScreenMode()
        initMapView()
    }

    private fun initMapView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.arrival_map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.arrival_map_fragment, it)
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
        setInitCamera()
        viewModel.getMarkerData(selectedLocation.latitude, selectedLocation.longitude)
    }

    private fun setInitCamera() {
        moveCamera(selectedLocation.latitude, selectedLocation.longitude)
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        val locate = CameraUpdate.scrollTo(
            LatLng(latitude, longitude)
        )
        naverMap.moveCamera(locate)
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it){
                    is ArrivalMapEvent.SetMarkers -> {
                        viewModel.uiState.value.markerDataList.forEach { data ->
                            setMarker(data)
                        }
                    }
                    is ArrivalMapEvent.SelectMarker -> {
                        moveCamera(it.latitude, it.longitude)
                    }
                    is ArrivalMapEvent.SelectArrival -> {
                        createPartyViewModel.setArrivalInfo(
                            it.spotId,
                            it.name
                        )
                        findNavController().toCreateParty()
                    }

                    is ArrivalMapEvent.NavigateToBack -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setMarker(data: UiMarkerItem) {
        val marker = Marker()
        binding.tvMarkerText.text = data.landMark
        marker.position = LatLng(data.latitude, data.longitude)
        marker.icon = OverlayImage.fromView(binding.marker)
        marker.setOnClickListener {
            binding.tvMarkerText.text = viewModel.uiState.value.selectedMarkerData.landMark
            selectedMarker?.icon = OverlayImage.fromView(binding.marker)
            binding.tvSelectedMarkerText.text = data.landMark
            marker.icon = OverlayImage.fromView(binding.markerSelected)
            viewModel.selectMarker(data)
            true
        }
        marker.map = naverMap
        markerList.add(marker)
    }



    private fun NavController.toCreateParty() {
        val action = ArrivalMapFragmentDirections.actionArrivalMapFragmentToCreatePartyFragment()
        navigate(action)
    }
}