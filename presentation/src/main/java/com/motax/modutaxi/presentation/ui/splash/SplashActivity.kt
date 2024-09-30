package com.motax.modutaxi.presentation.ui.splash

import android.animation.Animator
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
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.splash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                viewModel.checkLoginType()
            }

            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}
        })

        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is SplashUiEvent.NavigateToIntro -> toIntroActivity()
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