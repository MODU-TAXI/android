package com.motax.modutaxi.presentation.ui.main.showparty.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.FragmentSelectSpotFilterBottomSheetBinding
import com.motax.modutaxi.presentation.ui.main.showparty.ShowPartyViewModel
import com.motax.modutaxi.presentation.ui.main.showparty.adapter.SpotFilterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SelectSpotFilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectSpotFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectSpotFilterBottomSheetViewModel by viewModels()
    private val showPartyViewModel: ShowPartyViewModel by activityViewModels()
    private var adapter: SpotFilterAdapter? = null

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_spot_filter_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SpotFilterAdapter()
        binding.rvSpotList.adapter = adapter
        viewModel.getSpotList()

        initStateObserve()
        initEventObserve()
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiSpotList.collectLatest {
                adapter?.submitList(it)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collectLatest {
                when (it) {
                    is SelectSpotFilterEvent.SelectSpot -> {
                        showPartyViewModel.setSpotFilter(it.id, it.name)
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

}