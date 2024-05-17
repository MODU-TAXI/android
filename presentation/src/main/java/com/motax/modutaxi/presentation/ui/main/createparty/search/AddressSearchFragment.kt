package com.motax.modutaxi.presentation.ui.main.createparty.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAddressSearchBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.search.adapter.AddressSearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressSearchFragment :
    BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {

    private val viewModel: AddressSearchViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initEventObserve()

        parentViewModel.setNotFullScreenMode()
        binding.rvSearchResult.adapter = AddressSearchResultAdapter()
        binding.rvSearchResult.itemAnimator = null

        binding.etSearch.requestFocus()
        showKeyboard(binding.etSearch)

        setupSearchEditText()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is AddressSearchEvent.NavigateToBack -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun setupSearchEditText() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.updateKeyword(text.toString())
        }
    }

    private fun showKeyboard(view: EditText) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}