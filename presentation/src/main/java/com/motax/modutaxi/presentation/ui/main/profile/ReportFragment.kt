package com.motax.modutaxi.presentation.ui.main.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentReportBinding
import com.motax.modutaxi.presentation.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(R.layout.fragment_report) {

    private val viewModel: ReportViewModel by viewModels()
    private val args: ReportFragmentArgs by navArgs()
    private val targetId by lazy { args.targetId }
    private val roomId by lazy { args.roomId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        viewModel.setTargetAndRoomIds(targetId, roomId)
        initEventObserve()
        setupDropdownMenu()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ReportEvent.ShowLoading -> showLoading(requireContext())
                    is ReportEvent.DismissLoading -> dismissLoading()
                    is ReportEvent.ShowToastMessage -> showToastMessage(it.msg)
                    is ReportEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun setupDropdownMenu() {

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            viewModel.updateReportType(resources.getStringArray(R.array.report_type_array)[position])
        }
    }

}