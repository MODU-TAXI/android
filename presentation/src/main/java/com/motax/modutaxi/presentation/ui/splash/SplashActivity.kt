package com.motax.modutaxi.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.motax.modutaxi.presentation.base.BaseActivity
import com.motax.modutaxi.presentation.databinding.ActivitySplashBinding
import com.motax.modutaxi.presentation.ui.intro.IntroActivity
import com.motax.modutaxi.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeatOnStarted {
            delay(2000)
            viewModel.getAutoLogin()
        }
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is SplashUiEvent.NavigateToIntro -> toMainActivity()
                    is SplashUiEvent.NavigateToMain -> toMainActivity()
                }
            }
        }
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }
}