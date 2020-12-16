package com.udacity.asteroidradar.ui.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherViewModel @ViewModelInject constructor() : ViewModel() {

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean> get() = _navigateToMainScreen

    fun startLoading() {
        viewModelScope.launch {
            delay(1000L)
            _navigateToMainScreen.postValue(true)
        }
    }

    fun resetNavigation() {
        _navigateToMainScreen.postValue(false)
    }
}