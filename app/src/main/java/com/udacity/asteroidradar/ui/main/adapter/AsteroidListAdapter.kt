package com.udacity.asteroidradar.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.model.Asteroid

class AsteroidListAdapter(private val listener: ItemClickListener) :
    ListAdapter<Asteroid, AsteroidListViewHolder>(AsteroidListItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidListViewHolder {
        return AsteroidListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidListViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}