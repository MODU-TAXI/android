package com.motax.modutaxi.presentation.ui.taxipotlist

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.PopupMenu
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

    private var currentSortOption: SortOption = SortOption.LATEST

    enum class SortOption {
        LATEST, DEADLINE
    }

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

        binding.tvSortingOption.setOnClickListener { showSortingPopup(it) }
    }

    private fun showSortingPopup(view: View) {
        val popup = PopupMenu(view.context, view)

        when (currentSortOption) {
            SortOption.LATEST -> {
                popup.menu.add(Menu.NONE, R.id.action_deadline, Menu.NONE, "마감임박순")
            }
            SortOption.DEADLINE -> {
                popup.menu.add(Menu.NONE, R.id.action_latest, Menu.NONE, "최신순")
            }
        }

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_latest -> {
                    binding.tvSortingOption.text = "최신순"
                    currentSortOption = SortOption.LATEST
                    //Todo "최신순" 정렬 로직
                    true
                }
                R.id.action_deadline -> {
                    binding.tvSortingOption.text = "마감임박순"
                    currentSortOption = SortOption.DEADLINE

                    //Todo 마감임박순 정렬 로직
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}