package com.motax.modutaxi.presentation.ui.main.matchdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMatchDetailBinding
import com.motax.modutaxi.presentation.ui.main.matchdetail.adapter.ParticipantAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment :
    BaseFragment<FragmentMatchDetailBinding>(R.layout.fragment_match_detail) {

    private val viewModel: MatchDetailViewModel by viewModels()
    private lateinit var participantAdapter: ParticipantAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        participantAdapter = ParticipantAdapter()
        binding.rvParticipants.adapter = participantAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                participantAdapter.submitList(uiState.participantList)
            }
        }

        val roomId = 18L
        viewModel.getRoom(roomId)
    }


}