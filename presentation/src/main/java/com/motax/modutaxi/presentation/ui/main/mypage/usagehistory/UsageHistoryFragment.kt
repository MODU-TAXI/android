package com.motax.modutaxi.presentation.ui.main.mypage.usagehistory

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentUsageHistoryBinding
import com.motax.modutaxi.presentation.ui.main.home.HomeEvent
import com.motax.modutaxi.presentation.ui.main.mypage.usagedetail.UsageDetailsFragmentArgs
import com.motax.modutaxi.presentation.ui.main.mypage.usagehistory.adapter.UsageHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsageHistoryFragment : BaseFragment<FragmentUsageHistoryBinding>(R.layout.fragment_usage_history) {

    private val viewModel: UsageHistoryViewModel by viewModels()
    private var adapter: UsageHistoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = UsageHistoryAdapter()
        binding.rvUsageHistory.adapter = adapter

        initEventObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is UsageHistoryEvent.NavigateToUsageDetail -> findNavController().toUsageDetail(it.id)
                    is UsageHistoryEvent.NavigateToMyPage -> findNavController().navigateUp()
                    is UsageHistoryEvent.ShowMonthPicker -> showMonthYearPicker()
                }
            }
        }
    }

    private fun showMonthYearPicker() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_month_year_picker, null)
        val monthPicker: NumberPicker = dialogView.findViewById(R.id.picker_month)
        val yearPicker: NumberPicker = dialogView.findViewById(R.id.picker_year)
        val okButton: Button = dialogView.findViewById(R.id.button_ok)

        // 초기 설정
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        yearPicker.minValue = 2024
        yearPicker.maxValue = 2034

        monthPicker.value = viewModel.uiState.value.month
        yearPicker.value = viewModel.uiState.value.year

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            val selectedMonth = monthPicker.value
            val selectedYear = yearPicker.value
            viewModel.updateYearMonth(selectedYear, selectedMonth)
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun NavController.toUsageDetail(id: Long) {
        val action = UsageHistoryFragmentDirections.actionHistoryToDetail(id)
        navigate(action)
    }

}