package com.motax.modutaxi.presentation.ui.main.createparty.map

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentArrivalMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArrivalMapFragment: BaseFragment<FragmentArrivalMapBinding>(R.layout.fragment_arrival_map) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelect.setOnClickListener {
            findNavController().toCreateParty()
        }
    }

    private fun NavController.toCreateParty(){
        val action = ArrivalMapFragmentDirections.actionArrivalMapFragmentToCreatePartyFragment()
        navigate(action)
    }
}