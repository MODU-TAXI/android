package com.motax.modutaxi.presentation.ui.main.mypage.withdrawal.reason

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentWithdrawalReasonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReasonFragment : BaseFragment<FragmentWithdrawalReasonBinding>(R.layout.fragment_withdrawal_reason){

    private val viewModel: ReasonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        viewModel.loadMemberData()

        binding.rgReason.setOnCheckedChangeListener { group, checkedId ->
            val reason = when (checkedId) {
                R.id.rb_not_use -> "택시를 잘 이용하지 않아요"
                R.id.rb_dont_know -> "앱 사용 방법을 모르겠어요"
                R.id.rb_error -> "앱 오류가 많아요"
                R.id.rb_resignup -> "재가입이 필요해요"
                R.id.rb_etc -> "기타"
                else -> ""
            }
            viewModel.onReasonSelected(reason)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (!state.showEtcReason) {
                    binding.tvEtcReason.setText("")
                }
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it) {
                    is ReasonEvent.NavigateToConfirm -> findNavController().toConfirm()
                }
            }
        }
    }

    private fun NavController.toConfirm() {
        val action = ReasonFragmentDirections.actionReasonToConfirm()
        navigate(action)
    }
}