package com.motax.modutaxi.presentation.ui.main.matchdetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMatchDetailBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.matchdetail.adapter.ParticipantAdapter
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment :
    BaseFragment<FragmentMatchDetailBinding>(R.layout.fragment_match_detail), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: MatchDetailViewModel by viewModels()
    private val args: MatchDetailFragmentArgs by navArgs()
    private val pathList = mutableListOf<PathOverlay>()
    private val roomId by lazy { args.id }

    private var participantAdapter: ParticipantAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        parentViewModel.setNotFullScreenMode()
        binding.vm = viewModel
        participantAdapter = ParticipantAdapter()
        binding.rvParticipants.adapter = participantAdapter

        initMapView()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().toHome()
        }
    }

    private fun initMapView() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.match_detail_map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.match_detail_map_fragment, it)
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
        viewModel.getTaxiPotDetail(roomId)
        initStateObserve()
    }

    private fun initStateObserve(){
        repeatOnStarted {
            viewModel.uiState.collect{
                if(it.matchDetailUiData.path.isNotEmpty()){
                    setPath()
                }
            }
        }
    }

    private fun setPath() {
        val path = PathOverlay()
        val list = viewModel.uiState.value.matchDetailUiData.path
        path.coords = list

        path.color = Color.RED
        path.map = naverMap

        moveCamera(LatLng(list[0].latitude, list[0].longitude), LatLng(list[list.size - 1].latitude, list[list.size - 1].longitude))
    }

    private fun moveCamera(start: LatLng, end:LatLng) {

        val bounds = LatLngBounds.Builder()
            .include(start)
            .include(end)
            .build()

        val padding = resources.getDimensionPixelSize(R.dimen.map_padding)
        val cameraUpdate = CameraUpdate.fitBounds(bounds, padding)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun NavController.toHome() {
        val action = MatchDetailFragmentDirections.actionMatchDetailFragmentToHomeFragment()
        navigate(action)
    }
}