package com.motax.modutaxi.presentation.ui.main.showparty.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotListBottomSheetBinding
import com.motax.modutaxi.presentation.ui.main.showparty.ShowPartyViewModel
import com.motax.modutaxi.presentation.ui.main.showparty.adapter.TaxiPotFilterAdapter
import com.motax.modutaxi.presentation.ui.main.showparty.adapter.TaxiPotListAdapter
import com.motax.modutaxi.presentation.util.Constants.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaxiPotListBottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentTaxipotListBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShowPartyViewModel by activityViewModels()

    private var taxiPotAdapter : TaxiPotListAdapter ? = null

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
            R.layout.fragment_taxipot_list_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        taxiPotAdapter = TaxiPotListAdapter()
        binding.rvFilter.adapter = TaxiPotFilterAdapter()
        binding.rvTaxipotList.adapter = taxiPotAdapter

        setBottomSheetState()
        initStateObserve()
    }

    private fun initStateObserve(){
        repeatOnStarted {
            viewModel.bottomSheetUiState.collectLatest{
                taxiPotAdapter?.submitList(it.taxiPotList)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setBottomSheetState() {
        val behavior = BottomSheetBehavior.from(binding.taxipotListBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                viewModel.changeBottomSheetState(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewModel.changeBottomSheetHeight(slideOffset)
            }
        })
    }
}