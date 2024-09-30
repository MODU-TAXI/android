package com.motax.modutaxi.presentation.ui.main.chat.image

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentImageEnlargeBinding

class ImageEnlargeFragment :
    BaseFragment<FragmentImageEnlargeBinding>(R.layout.fragment_image_enlarge) {

    private val args: ImageEnlargeFragmentArgs by navArgs()
    private val url by lazy { args.imageUrl }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(url)
            .into(binding.iv)

        binding.btnExit.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}