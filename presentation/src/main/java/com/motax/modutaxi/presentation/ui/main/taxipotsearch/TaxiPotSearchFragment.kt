package com.motax.modutaxi.presentation.ui.main.taxipotsearch

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentTaxipotSearchBinding
import com.motax.modutaxi.presentation.ui.main.taxipotsearch.adapter.TaxiPotSearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxiPotSearchFragment :
    BaseFragment<FragmentTaxipotSearchBinding>(R.layout.fragment_taxipot_search) {

    private val viewModel: TaxiPotSearchViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        binding.rvSearchResult.adapter = TaxiPotSearchResultAdapter()
        binding.rvSearchResult.itemAnimator = null
        initStateObserve()

        viewModel.focusOnSearch()
        setupSearchEditText()

    }

    private fun setupSearchEditText() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.updateKeyword(text.toString())
        }
    }


    private fun initStateObserve() {
        repeatOnStarted {
//            viewModel.uiState.collect {
//                when (it.focusedField) {
//                    FocusedField.Search -> {
//                        binding.etSearch.requestFocus()
//                        showKeyboard(binding.etSearch)
//                    }
//
//                    FocusedField.NONE -> {
//                        binding.etSearch.clearFocus()
//                        hideKeyboard()
//                    }
//                }
//            }
        }
    }

    private fun showKeyboard(view: EditText) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}