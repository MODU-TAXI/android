package com.motax.modutaxi.presentation.ui.main.createparty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentCreatePartyBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel

class CreatePartyFragment: BaseFragment<FragmentCreatePartyBinding>(R.layout.fragment_create_party) {

    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.setNotFullScreenMode()

        binding.tvDeparture.setOnClickListener {
            findNavController().toMap()
        }
    }


    private fun NavController.toMap(){
        val action = CreatePartyFragmentDirections.actionCreatePartyFragmentToMapFragment()
        navigate(action)
    }


}