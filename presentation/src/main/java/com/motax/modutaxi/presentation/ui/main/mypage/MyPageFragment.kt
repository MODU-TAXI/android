package com.motax.modutaxi.presentation.ui.main.mypage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentMypageBinding
import com.motax.modutaxi.presentation.ui.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage){

    private val viewModel: MyPageViewModel by viewModels()

    private var galleryLauncher: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initEventObserve()

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

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is MyPageEvent.NavigateToEditNick -> findNavController().toEditNick()
                    is MyPageEvent.NavigateToUpdateProfile -> findNavController().toUpdateProfile()
                    is MyPageEvent.NavigateToEditEmail -> findNavController().toEditEmail()
                    is MyPageEvent.NavigateToInquiry -> findNavController().toInquiry()
                    is MyPageEvent.NavigateToUsageHistory -> findNavController().toUsageHistory()
                    is MyPageEvent.NavigateToWithdrawal -> findNavController().toWithdrawal()
                    is MyPageEvent.NavigateToAccount -> findNavController().toAccount()
                    is MyPageEvent.NavigateToIntro -> navigateToIntro()
                    is MyPageEvent.ShowToastMessage -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun NavController.toEditNick() {
        val action = MyPageFragmentDirections.actionMypageToEditNick()
        navigate(action)
    }

    private fun NavController.toUpdateProfile() {
        val action = MyPageFragmentDirections.actionMyPageToUpdateProfile()
        navigate(action)
    }

    private fun NavController.toEditEmail() {
        val action = MyPageFragmentDirections.actionMyPageToAuthorizeSchool()
        navigate(action)
    }

    private fun NavController.toInquiry() {
        val action = MyPageFragmentDirections.actionMyPageToInquiry()
        navigate(action)
    }

    private fun NavController.toUsageHistory() {
        val action = MyPageFragmentDirections.actionMyPageToUsageHistory()
        navigate(action)
    }

    private fun NavController.toWithdrawal() {
        val action = MyPageFragmentDirections.actionMyPageToWithdrawal()
        navigate(action)
    }

    private fun NavController.toAccount() {
        val action = MyPageFragmentDirections.actionMyPageToAccount()
        navigate(action)
    }

    private fun navigateToIntro() {
        val intent = Intent(requireContext(), IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}