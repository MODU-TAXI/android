package com.motax.modutaxi.presentation.ui.intro.signup.authorization

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseFragment
import com.motax.modutaxi.presentation.databinding.FragmentAuthorizationGuideBinding

class AuthorizationGuideFragment :
    BaseFragment<FragmentAuthorizationGuideBinding>(R.layout.fragment_authorization_guide) {

    private lateinit var neededPermissionList: ArrayList<String>
    private val requiredPermissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(  // 안드로이드 13 이상 필요한 권한들
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            arrayOf(  // 안드로이드 13 미만 필요한 권한들
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener();
    }

    private fun setBtnListener() {
        binding.btnCheck.setOnClickListener {
            onCheckPermissions()
        }
    }

    private fun onCheckPermissions(){
        neededPermissionList = arrayListOf()

        requiredPermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) neededPermissionList.add(permission)
        }

        if (neededPermissionList.isNotEmpty()) {
            activityResultLauncher.launch(neededPermissionList.toTypedArray())
        } else {
            binding.btnCheck.isEnabled = true
            binding.btnCheck.setBackgroundResource(R.drawable.rect_black0_fill_nostroke_61radius)
        }
    }

    private val contract = ActivityResultContracts.RequestMultiplePermissions()

    private val activityResultLauncher = registerForActivityResult(contract){ resultMap ->
        val isAllGranted = requiredPermissionList.all{ e-> resultMap[e] == true}

        // todo 분기처리 필요하면 하기
        if(isAllGranted){

        } else {

        }

        binding.btnCheck.text = "다음으로"
        binding.btnCheck.setOnClickListener {
            findNavController().toIdentification()
        }
    }



    private fun NavController.toIdentification() {
        val action =
            AuthorizationGuideFragmentDirections.actionAuthorizationGuideFragmentToIdentificationFragment()
        navigate(action)
    }
}