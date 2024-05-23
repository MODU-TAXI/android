package com.motax.modutaxi.presentation.ui.main.createparty.arrival

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalMapBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalMapFragment : BaseFragment<FragmentArrivalMapBinding>(R.layout.fragment_arrival_map),
    OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: ArrivalMapViewModel by activityViewModels()
    private val args: ArrivalMapFragmentArgs by navArgs()

    private val selectedLocation by lazy { args.selectLocation }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
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

    private fun NavController.toCreateParty() {
        val action = ArrivalMapFragmentDirections.actionArrivalMapFragmentToCreatePartyFragment()
        navigate(action)
    }
}