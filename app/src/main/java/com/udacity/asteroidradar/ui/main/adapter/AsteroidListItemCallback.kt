package com.udacity.asteroidradar.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.model.Asteroid

object AsteroidListItemCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}