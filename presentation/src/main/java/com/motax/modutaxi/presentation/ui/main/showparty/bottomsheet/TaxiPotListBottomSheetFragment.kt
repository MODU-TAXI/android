package com.motax.modutaxi.presentation.ui.main.showparty.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.customview.FilterPopUpMenu
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotListBottomSheetBinding
import com.motax.modutaxi.presentation.ui.main.showparty.ShowPartyViewModel
import com.motax.modutaxi.presentation.ui.main.showparty.TaxiPotSortType
import com.motax.modutaxi.presentation.ui.main.showparty.adapter.TaxiPotFilterAdapter
import com.motax.modutaxi.presentation.ui.main.showparty.adapter.TaxiPotListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaxiPotListBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTaxipotListBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShowPartyViewModel by activityViewModels()

    private var taxiPotAdapter: TaxiPotListAdapter? = null
    private var taxiPotFilterAdapter: TaxiPotFilterAdapter? = null
    private val popupLocation = IntArray(2)

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
        taxiPotFilterAdapter = TaxiPotFilterAdapter()
        binding.rvFilter.adapter = taxiPotFilterAdapter
        binding.rvTaxipotList.adapter = taxiPotAdapter
        binding.rvFilter.itemAnimator = null
        binding.tvSortingOption.setOnClickListener {
            showPopup()
        }

        setBottomSheetState()
        initStateObserve()
    }

    private fun showPopup() {
        val sortType = binding.tvSortingOption
        sortType.getLocationOnScreen(popupLocation)
        val left = popupLocation[0] + sortType.left.toFloat()
        val top = popupLocation[1] + sortType.bottom.toFloat()
        FilterPopUpMenu(requireContext(), viewModel.bottomSheetUiState.value.sortType, ::setFilter).show(left.toInt(),top.toInt())
    }

    private fun setFilter(sortType: TaxiPotSortType) {
        viewModel.setFilter(sortType)
        binding.tvSortingOption.text = sortType.uiText
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiState.collectLatest {
                taxiPotAdapter?.submitList(it.taxiPotList)
            }
        }

        repeatOnStarted {
            viewModel.bottomSheetUiState.collectLatest {
                taxiPotFilterAdapter?.submitList(it.filterList)
            }
        }

        repeatOnStarted {
            viewModel.bottomSheetUiState.collect {
                if (it.spotFilter.isBlank()) {
                    binding.layoutSelectSpot.setBackgroundResource(R.drawable.rect_nofill_gray200stroke_101radius)
                    binding.ivSmallFlag.setImageResource(R.drawable.ic_small_flag_gray200)
                    binding.tvFilterName.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.mx_gray700
                        )
                    )
                    binding.tvFilterName.text = "거점지"
                    binding.btnCancelFilter.visibility = View.GONE
                } else {
                    binding.layoutSelectSpot.setBackgroundResource(R.drawable.rect_sub500fill_nostroke_101radius)
                    binding.ivSmallFlag.setImageResource(R.drawable.ic_circle_flag)
                    binding.tvFilterName.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    binding.tvFilterName.text = it.spotFilter
                    binding.btnCancelFilter.visibility = View.VISIBLE
                }
            }
        }

        repeatOnStarted {
            viewModel.bottomSheetUiState.collect {
                if (it.isImminent) {
                    binding.btnDeadlineImminent.setImageResource(R.drawable.ic_eclipse_fill)
                } else {
                    binding.btnDeadlineImminent.setImageResource(R.drawable.ic_eclipse_no_fill)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setBottomSheetState() {
        val behavior = BottomSheetBehavior.from(binding.taxipotListBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                viewModel.changeBottomSheetState(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewModel.changeBottomSheetHeight(slideOffset)
            }
        })
    }
}