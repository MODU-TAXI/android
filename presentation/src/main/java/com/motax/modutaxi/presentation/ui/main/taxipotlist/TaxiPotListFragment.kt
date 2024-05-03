package com.motax.modutaxi.presentation.ui.main.taxipotlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.main.taxipotlist.adapter.TaxipotAdapter
import com.motax.modutaxi.presentation.ui.main.taxipotlist.model.TaxiPotCategory
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxiPotListFragment :
    BaseFragment<FragmentTaxipotListBinding>(R.layout.fragment_taxipot_list) {

    private val viewModel: TaxiPotListViewModel by viewModels()

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

        viewModel.uiTaxiPotItemList.observe(viewLifecycleOwner) { taxipots ->
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

        setupCategoryFilters()
        viewModel.loadTaxipots()
    }

    private fun setupCategoryFilters() {
        binding.tvStudentVerification.setOnClickListener {
            viewModel.toggleCategorySelection(TaxiPotCategory.STUDENT_VERIFICATION)
            updateCategoryUI()
        }

        binding.tvWomenOnly.setOnClickListener {
            viewModel.toggleCategorySelection(TaxiPotCategory.FEMALES_ONLY)
            updateCategoryUI()
        }

        binding.tvMannersBoarding.setOnClickListener {
            viewModel.toggleCategorySelection(TaxiPotCategory.QUIET)
            updateCategoryUI()
        }

        binding.btnDeadlineImminent.setOnClickListener {
            viewModel.toggleCategorySelection(TaxiPotCategory.DEADLINE)
            updateCategoryUI()
        }
    }

    private fun updateCategoryUI() {
        val selectedColor =
            ContextCompat.getColor(requireContext(), R.color.taxipot_list_selected_category)
        val unselectedColor =
            ContextCompat.getColor(requireContext(), R.color.taxipot_list_unselected_category)

        binding.tvStudentVerification.apply {
            isSelected = viewModel.isCategorySelected(TaxiPotCategory.STUDENT_VERIFICATION)
            setTextColor(if (isSelected) selectedColor else unselectedColor)
        }

        binding.tvWomenOnly.apply {
            isSelected = viewModel.isCategorySelected(TaxiPotCategory.FEMALES_ONLY)
            setTextColor(if (isSelected) selectedColor else unselectedColor)
        }

        binding.tvMannersBoarding.apply {
            isSelected = viewModel.isCategorySelected(TaxiPotCategory.QUIET)
            setTextColor(if (isSelected) selectedColor else unselectedColor)
        }

        binding.btnDeadlineImminent.setImageResource(
            if (viewModel.isCategorySelected(TaxiPotCategory.DEADLINE)) R.drawable.btn_eclipse_red_fill
            else R.drawable.btn_ellipse_no_fill
        )
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
