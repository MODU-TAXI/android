package com.motax.modutaxi.presentation.ui.main.createparty.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMapBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private val pathList = mutableListOf<PathOverlay>()

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: MapViewModel by viewModels()

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
    }

    override fun onMapReady(nM: NaverMap) {
        this.naverMap = nM
        with(naverMap.uiSettings) {
            isCompassEnabled = false
            isZoomControlEnabled = false
        }
        setMapListener()
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
                cameraPosition.latitude.toString(),
                cameraPosition.longitude.toString()
            )
        }

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
        val action = MapFragmentDirections.actionMapFragmentToTaxiPotSearchFragment()
        navigate(action)
    }


}