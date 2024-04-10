package com.motax.modutaxi.presentation.ui.taxipotlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
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

    //필터링 - 마감임박
    private var isDeadlineFiltered = false;

    //정렬 기준 - 최신순, 마감임박순
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


        //팝업메뉴
        binding.tvSortingOption.setOnClickListener { showSortingPopup(it) }

        //스와이프 리프레시
        binding.swipeRefreshLayout.setOnRefreshListener {
            //Todo sorting option enum 값따라서 해당 분류 api로 조회
            viewModel.loadTaxipots()

            binding.swipeRefreshLayout.isRefreshing = false
        }

        //마감임박 리스너
        binding.btnDeadlineImminent.setOnClickListener {
            //필터 상태 변경
            isDeadlineFiltered = !isDeadlineFiltered

            //도형 이미지 업데이트
            updateDeadlineIcon()

            //마감임박 택시팟만 필터링
            filterDeadlineTaxipots()
        }

        viewModel.loadTaxipots()
    }

    private fun updateDeadlineIcon() {
        if (isDeadlineFiltered) {
            binding.btnDeadlineImminent.setImageResource(R.drawable.btn_eclipse_red_fill)
        } else {
            binding.btnDeadlineImminent.setImageResource(R.drawable.btn_ellipse_no_fill)

        }
    }

    private fun filterDeadlineTaxipots() {
        if (isDeadlineFiltered) {
            // 마감임박 택시팟만 필터링하여 표시하는 로직
            viewModel.filterDeadlineTaxipots()
        } else {
            // 모든 택시팟을 표시하는 로직
            viewModel.loadTaxipots()
        }
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
