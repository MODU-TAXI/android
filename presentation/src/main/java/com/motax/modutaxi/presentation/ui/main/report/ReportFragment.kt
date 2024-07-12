package com.motax.modutaxi.presentation.ui.main.report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(R.layout.fragment_report) {

    private val viewModel: ReportViewModel by viewModels()
    private val args: ReportFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        //viewModel.setTargetAndRoomIds(args.targetId, args.roomId)

        //임시 args받기
        arguments?.let {
            val targetId = it.getLong("targetId") ?: 1L
            val roomId = it.getLong("roomId") ?: 1L
            viewModel.setTargetAndRoomIds(targetId, roomId)
        }

        //임시
        viewModel.fetchMemberProfile(7)
        viewModel.fetchTaxiPotPreview(43)

        setupDropdownMenu()
        setupReportContentEditText()
    }

    private fun setupDropdownMenu() {
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
            setAdapter(arrayAdapter)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_color))
            setOnItemClickListener { parent, view, position, id ->
                val selectedTextView = view as TextView
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.selected_text_color
                    )
                )
                viewModel.updateReportType(options[position])
            }
            setOnDismissListener {
                if (text.isNullOrEmpty()) {
                    viewModel.updateReportType("")
                }
            }
        }
    }

    private fun setupReportContentEditText() {
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateReportContent(s.toString())
            }
        })
    }

}