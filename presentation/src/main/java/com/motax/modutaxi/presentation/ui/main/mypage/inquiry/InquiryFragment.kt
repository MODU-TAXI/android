package com.motax.modutaxi.presentation.ui.main.mypage.inquiry

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentInquiryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry){

    private val viewModel: InquiryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is InquiryEvent.OpenKakao -> openKakaoChat()
                    is InquiryEvent.NavigateToMyPage -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun openKakaoChat() {
        val kakaoUrl = "https://open.kakao.com/o/sIHBVkzg" // 여기에 카카오톡 오픈채팅방 URL을 입력해
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl))
        startActivity(intent)
    }
}