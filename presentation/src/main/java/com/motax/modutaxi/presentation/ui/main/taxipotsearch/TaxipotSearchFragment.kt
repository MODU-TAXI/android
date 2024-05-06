package com.motax.modutaxi.presentation.ui.main.taxipotsearch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotSearchBinding
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.adapter.TaxiPotSearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaxipotSearchFragment :
    BaseFragment<FragmentTaxipotSearchBinding>(R.layout.fragment_taxipot_search) {

    private val viewModel: TaxipotSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvSearchResult.adapter = TaxiPotSearchResultAdapter()
        //viewModel.getSearchResults()

        viewModel.loadDummyData()
    }
}