package com.motax.modutaxi.presentation.ui.taxipotlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
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


    private fun showSortingPopup(anchorView: View) {
        val inflater = LayoutInflater.from(context)
        val popupView = inflater.inflate(R.layout.popup_sorting_menu, null)
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        popupView.findViewById<TextView>(R.id.tv_latest).setOnClickListener {

            binding.tvSortingOption.text = "최신순"
            //Todo 최신순 조회 API
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.tv_deadline).setOnClickListener {

            binding.tvSortingOption.text = "마감임박순"
            //Todo 마감임박순 조회 API
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchorView, 0, 0)
    }
}