package com.motax.modutaxi.presentation.ui.main.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentNotificationBinding
import com.motax.modutaxi.presentation.ui.main.notification.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val viewModel: NotificationViewModel by viewModels()
    private var adapter: NotificationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = NotificationAdapter()
        binding.rvNotification.adapter = adapter

        viewModel.loadNotifications()
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when(it) {
                    is NotificationEvent.NavigateToHome -> findNavController().navigateUp()
                }
            }
        }
    }
}