package com.motax.modutaxi.presentation.ui.main.report

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(R.layout.fragment_report) {

    private val viewModel: ReportViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        val options = resources.getStringArray(R.array.report_type_array)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_report_type, options)


        //드롭다운 메뉴 배경 설정
        binding.autoCompleteTextView.apply {
            setDropDownBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.rect_gray_60efefef_fill_nostroke_8radius,
                    null
                )
            )
        }

        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.autoCompleteTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_color))
        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedTextView = view as TextView
            binding.autoCompleteTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.selected_text_color))
            viewModel.selectReportType(true)

        }

        binding.autoCompleteTextView.setOnDismissListener {
            if (binding.autoCompleteTextView.text.isNullOrEmpty()) {
                viewModel.selectReportType(false)
            }
        }
    }


}