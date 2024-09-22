package com.motax.modutaxi.presentation.ui.main.notification

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        adapter = NotificationAdapter(viewModel)
        binding.rvNotification.adapter = adapter

        viewModel.loadNotifications()
        initEventObserve()
        setScrollEventListener()
        onRefresh()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is NotificationEvent.NavigateToHome -> findNavController().navigateUp()
                    is NotificationEvent.NavigateToMatchingDetail -> findNavController().toNavigateToMatchDetail(
                        it.id
                    )

                    is NotificationEvent.ShowAlertAndNavigateHome -> showAlertAndNavigateHome()
                }
            }
        }
    }

    private fun setScrollEventListener() {

        binding.rvNotification.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                if (lastVisibleItemPosition == itemTotalCount) {
                    viewModel.loadNotifications()
                }
            }
        })
    }

    private fun onRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }


    private fun NavController.toNavigateToMatchDetail(id: Long) {
        val action = NotificationFragmentDirections.actionNotificationToMatchdetail(id)
        navigate(action)
    }

    private fun showAlertAndNavigateHome() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("삭제된 방입니다.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .show()
    }
}
