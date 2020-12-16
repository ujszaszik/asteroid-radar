package com.udacity.asteroidradar.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.network.NetworkStatus
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.ui.main.binding.combineWith
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: AsteroidRepository
) : ViewModel() {

    val asteroidList = repository.asteroids
    val asteroidListNetworkStatus = repository.asteroidListNetworkStatus

    val currentPictureOfTheDay = repository.pictureOfTheDay
    val currentPictureOfTheDayNetworkStatus = repository.asteroidPictureNetworkStatus

    val showEmptyListLayout: LiveData<Boolean> =
        asteroidList.combineWith(asteroidListNetworkStatus) { asteroidList, networkStatus ->
            asteroidList.isNullOrEmpty() && networkStatus != NetworkStatus.Loading
        }

    private val _navigateToDetails = MutableLiveData<Asteroid>()
    val navigateToDetails: LiveData<Asteroid> get() = _navigateToDetails

    init {
        viewModelScope.launch {
            repository.fetchAsteroids()
            repository.fetchPictureOfTheDay()
        }
    }

    fun onItemClicked(asteroid: Asteroid) {
        _navigateToDetails.postValue(asteroid)
    }

    fun resetNavigation() {
        _navigateToDetails.value = null
    }
}