package com.motax.modutaxi.presentation.ui.main.map

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay

class TestMapFragment: BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private val pathList = mutableListOf<PathOverlay>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapView()
    }

    private fun initMapView(){
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

        setPath()
    }

    private fun setPath(){
        val path = PathOverlay()
        val manager = resources.assets
        val inputStream = manager.open("test.json")
        val jsonString = inputStream.bufferedReader().use{ it.readText() }

        val gson = Gson()
        val data : TestMapData = gson.fromJson(jsonString, TestMapData::class.java)

        val list = data.route.traoptimal[0].path.map {
            LatLng(it[1], it[0])
        }

        path.coords = list

        path.color = Color.RED
        path.map = naverMap
    }

}