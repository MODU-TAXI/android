package com.motax.modutaxi.presentation.ui.main.managetaxipot.arrival

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalMapBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.managetaxipot.ManageTaxiPotViewModel
import com.motax.modutaxi.presentation.ui.main.managetaxipot.model.UiMarkerItem
import com.motax.modutaxi.presentation.util.Constants.TAG
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
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
    private var selectedMarker: Marker? = null

    private lateinit var naverMap: NaverMap
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ArrivalMapViewModel by viewModels()
    private val manageTaxiPotViewModel: ManageTaxiPotViewModel by activityViewModels()
    private val args: ArrivalMapFragmentArgs by navArgs()

    private val selectedLocation by lazy { args.selectLocation }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.setFullScreenMode()
        binding.vm = viewModel
        viewModel.setSearchKeyWord(selectedLocation.landMark.toString())
        initEventObserve()
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
        viewModel.getMarkerData(selectedLocation.latitude, selectedLocation.longitude)
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it){
                    is ArrivalMapEvent.SetMarkers -> {
                        viewModel.uiState.value.markerDataList.forEach { data ->
                            setMarker(data)
                        }

                        if(selectedLocation.isSpot){
                            clickSpot()
                        }
                    }
                    is ArrivalMapEvent.SelectArrival -> {
                        manageTaxiPotViewModel.setArrivalInfo(
                            it.spotId,
                            it.name
                        )
                        findNavController().toCreateParty()
                    }

                    is ArrivalMapEvent.NavigateToBack -> {
                        findNavController().navigateUp()
                    }

                    is ArrivalMapEvent.MoveCamera -> {
                        Log.d(TAG,it.start.toString())
                        Log.d(TAG,it.end.toString())
                        val bounds = LatLngBounds.Builder()
                            .include(it.start)
                            .include(it.end)
                            .build()

                        val padding = resources.getDimensionPixelSize(R.dimen.arrival_map_padding)
                        val cameraUpdate = CameraUpdate.fitBounds(bounds, padding)
                        naverMap.moveCamera(cameraUpdate)
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
            selectedMarker?.let{
                binding.tvMarkerText.text = viewModel.uiState.value.selectedMarkerData.landMark
                selectedMarker?.icon = OverlayImage.fromView(binding.marker)
            }
            binding.tvSelectedMarkerText.text = data.landMark
            marker.icon = OverlayImage.fromView(binding.markerSelected)
            selectedMarker = marker
            viewModel.selectMarker(data)
            true
        }
        marker.map = naverMap
        markerList.add(marker)
    }

    private fun clickSpot(){
        markerList[0].performClick()
    }

    private fun NavController.toCreateParty() {
        val action = ArrivalMapFragmentDirections.actionArrivalMapFragmentToManageTaxiPotFragment()
        navigate(action)
    }
}