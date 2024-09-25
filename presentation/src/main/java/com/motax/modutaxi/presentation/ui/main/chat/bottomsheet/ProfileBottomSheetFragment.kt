package com.motax.modutaxi.presentation.ui.main.chat.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.databinding.FragmentProfileBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val args: ProfileBottomSheetFragmentArgs by navArgs()
    private val id by lazy { args.id }

    private val viewModel: ProfileBottomSheetViewModel by viewModels()

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
            R.layout.fragment_profile_bottom_sheet,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMemberProfile(id)
        initStateObserve()
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

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiState.collect{
                binding.tvNick.text = it.nick
                if(!it.certified) {
                    binding.ivMark.visibility = View.GONE
                    binding.tvStudentCertification.visibility = View.GONE
                }
                binding.tvMatchingcount.text = it.matchingCount

                Glide.with(requireContext())
                    .load(it.profile)
                    .into(binding.ivProfile)
            }
        }
    }

}