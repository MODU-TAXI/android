package com.motax.modutaxi.presentation.ui.main.createparty.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAddressSearchBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.map.DepartureMapViewModel
import com.motax.modutaxi.presentation.ui.main.createparty.search.adapter.AddressSearchResultAdapter
import com.motax.modutaxi.presentation.util.Constants.ARRIVAL_SEARCH
import com.motax.modutaxi.presentation.util.Constants.DEPARTURE_SEARCH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressSearchFragment :
    BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {

    private val viewModel: AddressSearchViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val departureMapViewModel: DepartureMapViewModel by activityViewModels()

    private val args : AddressSearchFragmentArgs by navArgs()
    private val type by lazy{args.type}

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
                    is AddressSearchEvent.SelectLocation -> {
                        when(type){
                            DEPARTURE_SEARCH -> {
                                findNavController().navigateUp()
                                departureMapViewModel.selectLocationFromSearch(it.latitude, it.longitude, it.landMark)
                            }

                            ARRIVAL_SEARCH -> {
                                findNavController().toArrivalMap()
                            }
                        }
                    }
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

    private fun NavController.toArrivalMap(){
        val action = AddressSearchFragmentDirections.actionAddressSearchFragmentToArrivalMapFragment()
        navigate(action)
    }
}