package com.motax.modutaxi.presentation.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseActivity
import com.motax.modutaxi.presentation.chatmanager.ChatManager
import com.motax.modutaxi.presentation.databinding.ActivityMainBinding
import com.motax.modutaxi.presentation.ui.toMultiPart
import com.motax.modutaxi.presentation.util.Constants.STORAGE_PERMISSION
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel : MainViewModel by viewModels()
    private val chatManager : ChatManager by viewModels()

    private lateinit var neededPermissionList: MutableList<String>

    private val storagePermissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                // 안드로이드 13 이상 필요한 권한들
                Manifest.permission.READ_MEDIA_IMAGES,
                // Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(  // 안드로이드 13 미만 필요한 권한들
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBnv()
        setStatusBarFullScreen()
        initEventObserve()
    }

    private fun setBnv() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        with(binding) {
            bottomNavigationView.apply {
                itemIconTintList = null
                setupWithNavController(navController)
                setOnItemSelectedListener { item ->
                    NavigationUI.onNavDestinationSelected(item, navController)
                    navController.popBackStack(item.itemId, inclusive = false)
                    true
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.homeFragment  || destination.id == R.id.myPageFragment ||
                    destination.id == R.id.taxiPotFragment){
                    bottomNavigationView.visibility = View.VISIBLE
                } else {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is MainEvent.FullScreenMode -> {
                        setStatusBarFullScreen()
                    }

                    is MainEvent.NotFullScreenMode -> {
//                        WindowCompat.setDecorFitsSystemWindows(window, true)
                        setStatusBarNotFullScreen()
                    }

                    is MainEvent.GoToGallery -> {
                        onCheckStoragePermissions()
                    }
                }
            }
        }
    }

    private fun onCheckStoragePermissions() {
        neededPermissionList = mutableListOf()

        storagePermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) neededPermissionList.add(permission)
        }

        if (neededPermissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissionList.toTypedArray(),
                STORAGE_PERMISSION
            )
        } else {
            openGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data

                uri?.let {
                    viewModel.setImage(
                        it, it.toMultiPart(this)
                    )
                }
            }
        }


    private fun setStatusBarFullScreen(){
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun setStatusBarNotFullScreen(){
        window.apply {
            statusBarColor = Color.WHITE
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }


}