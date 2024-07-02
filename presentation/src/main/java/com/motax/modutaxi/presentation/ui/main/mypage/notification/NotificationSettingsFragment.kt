package com.motax.modutaxi.presentation.ui.main.mypage.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentNotificationSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationSettingsFragment : BaseFragment<FragmentNotificationSettingsBinding>(R.layout.fragment_notification_settings){

    private val viewModel: NotificationSettingsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }


}