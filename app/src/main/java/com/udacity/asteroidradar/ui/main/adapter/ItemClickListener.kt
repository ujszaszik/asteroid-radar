package com.udacity.asteroidradar.ui.main.adapter

import com.udacity.asteroidradar.model.Asteroid

class ItemClickListener(private val listener: (Asteroid) -> Unit) {

    fun performClick(asteroid: Asteroid) {
        listener.invoke(asteroid)
    }
}