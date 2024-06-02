package com.motax.modutaxi.presentation.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.base.BaseActivity
import com.motax.modutaxi.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel : MainViewModel by viewModels()

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
                if(destination.id == R.id.homeFragment || destination.id == R.id.showPartyFragment || destination.id == R.id.myPageFragment ||
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
                }
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