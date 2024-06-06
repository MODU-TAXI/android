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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotListBottomSheetBinding
import com.motax.modutaxi.presentation.ui.main.showparty.ShowPartyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TaxiPotListBottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentTaxipotListBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShowPartyViewModel by activityViewModels()

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

        setBottomSheetState()
    }

    private fun setBottomSheetState() {
        val behavior = BottomSheetBehavior.from(binding.root)
    }
}