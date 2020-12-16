package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.ui.main.adapter.AsteroidListAdapter
import com.udacity.asteroidradar.ui.main.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var listener: ItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel

        val manager = LinearLayoutManager(activity)
        binding.asteroidListRecyclerView.layoutManager = manager

        binding.lifecycleOwner = this

        listener = ItemClickListener { viewModel.onItemClicked(it) }


        observeAsteroidList()
        observePictureOfTheDay()
        observeItemClick()

        return binding.root
    }

    private fun observeAsteroidList() {
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            binding.asteroidListRecyclerView.adapter =
                AsteroidListAdapter(listener).apply {
                    submitList(it)
                }
        })
    }

    private fun observePictureOfTheDay() {
        viewModel.currentPictureOfTheDay.observe(viewLifecycleOwner, Observer {
            binding.pictureOfTheDay = it
        })
    }

    private fun observeItemClick() {
        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            it?.let { navigateToDetails(it) }
        })
    }

    private fun navigateToDetails(asteroid: Asteroid) {
        MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid).run {
            findNavController().navigate(this)
        }
        viewModel.resetNavigation()
    }

}