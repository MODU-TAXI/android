package com.motax.modutaxi.presentation.ui.taxipotlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.adapters.TaxipotAdapter
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxipotListFragment :
    BaseFragment<FragmentTaxipotListBinding>(R.layout.fragment_taxipot_list) {

    private val viewModel: TaxipotListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.taxipotList.layoutManager = layoutManager

        val taxipotAdapter = TaxipotAdapter()
        binding.taxipotList.adapter = taxipotAdapter
        
        viewModel.taxipotList.observe(viewLifecycleOwner) { taxipots ->
            taxipotAdapter.submitList(taxipots)
        }

        viewModel.loadTaxipots()
    }
}