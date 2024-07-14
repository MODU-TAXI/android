package com.motax.modutaxi.presentation.ui.main.mypage.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAccountManagementBinding
import com.motax.modutaxi.presentation.ui.main.mypage.account.adapter.AccountAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment :
    BaseFragment<FragmentAccountManagementBinding>(R.layout.fragment_account_management) {

    private val viewModel: AccountViewModel by viewModels()
    private var adapter: AccountAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = AccountAdapter(viewModel)
        binding.rvAccount.adapter = adapter

        initEventObserve()
    }




    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is AccountEvent.NavigateToMyPage -> findNavController().navigateUp()
                }
            }
        }
    }
}