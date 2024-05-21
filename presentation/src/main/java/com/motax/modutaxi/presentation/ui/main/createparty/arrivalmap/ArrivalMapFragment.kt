package com.motax.modutaxi.presentation.ui.main.createparty.arrivalmap

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalMapFragment: BaseFragment<FragmentArrivalMapBinding>(R.layout.fragment_arrival_map) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun NavController.toCreateParty(){
        val action = ArrivalMapFragmentDirections.actionArrivalMapFragmentToCreatePartyFragment()
        navigate(action)
    }
}