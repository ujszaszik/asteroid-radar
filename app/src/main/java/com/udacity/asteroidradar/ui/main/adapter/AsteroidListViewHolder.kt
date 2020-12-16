package com.udacity.asteroidradar.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidListRecyclerRowBinding
import com.udacity.asteroidradar.model.Asteroid


class AsteroidListViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private lateinit var binding: AsteroidListRecyclerRowBinding

        fun from(parent: ViewGroup): AsteroidListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            binding = AsteroidListRecyclerRowBinding.inflate(inflater, parent, false)
            return AsteroidListViewHolder(binding.root)
        }
    }

    fun bind(asteroid: Asteroid, listener: ItemClickListener) {
        binding.asteroid = asteroid
        binding.listener = listener
        binding.executePendingBindings()
    }
}