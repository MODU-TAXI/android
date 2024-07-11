package com.motax.modutaxi.presentation.ui.main.report

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(R.layout.fragment_report) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val options = resources.getStringArray(R.array.report_type_array)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_report_type, options)

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


    }


}