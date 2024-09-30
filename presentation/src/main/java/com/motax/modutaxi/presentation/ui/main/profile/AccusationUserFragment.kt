package com.motax.modutaxi.presentation.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAccusationUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccusationUserFragment: BaseFragment<FragmentAccusationUserBinding>(R.layout.fragment_accusation_user) {

    private val args: AccusationUserFragmentArgs by navArgs()
    val id by lazy{args.id}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

}