package com.motax.modutaxi.presentation.ui.main.mypage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage){

    private val viewModel: MyPageViewModel by viewModels()

    private var galleryLauncher: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let {uri ->
                    viewModel.uploadImageToS3(uri)
                }
            }
        }

        viewModel.loadMemberData()

        binding.ivProfileChange.setOnClickListener {
            showPopupMenu(requireContext(), it)
        }
    }

    private fun showPopupMenu(context: Context, anchor: View) {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menuInflater.inflate(R.menu.profile_image_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_set_default -> {
                    viewModel.setDefaultProfileImage()
                    true
                }

                R.id.action_select_from_album -> {
                    openGallery()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher?.launch(intent)
    }

}