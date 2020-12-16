package com.udacity.asteroidradar.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        viewModel.startLoading()
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigateToMainScreen.observe(this, Observer {
            if (it) {
                navigateToMainScreen()
                viewModel.resetNavigation()
            }
        })
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}