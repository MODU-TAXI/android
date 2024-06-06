package com.motax.modutaxi.presentation.ui.main.matchdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMatchDetailBinding
import com.motax.modutaxi.presentation.ui.main.MainViewModel
import com.motax.modutaxi.presentation.ui.main.matchdetail.adapter.ParticipantAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment :
    BaseFragment<FragmentMatchDetailBinding>(R.layout.fragment_match_detail) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: MatchDetailViewModel by viewModels()
    private val args: MatchDetailFragmentArgs by navArgs()
    private val roomId by lazy { args.id }

    private var participantAdapter: ParticipantAdapter?= null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.setNotFullScreenMode()
        binding.vm = viewModel
        participantAdapter = ParticipantAdapter()
        binding.rvParticipants.adapter = participantAdapter

        viewModel.getRoom(roomId)
    }


}