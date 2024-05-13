package com.motax.modutaxi.presentation.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.motax.modutaxi.presentation.base.BaseActivity
import com.motax.modutaxi.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEventObserve()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is MainEvent.FullScreenMode -> {
                        enableEdgeToEdge()
                    }

                    is MainEvent.NotFullScreenMode -> {
                        WindowCompat.setDecorFitsSystemWindows(window, true)
                    }
                }
            }
        }
    }


}