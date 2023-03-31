package com.example.fiwoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiwoapp.R
import com.example.fiwoapp.adapter.PopularMovieAdapter
import com.example.fiwoapp.databinding.FragmentMovieBinding
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: PopularMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingData()
        setUpRV()
    }

    private fun loadingData() {
        println("data coming")
        movieAdapter = PopularMovieAdapter()
        lifecycleScope.launch {
            println("lifecycle girdi")
            viewModel.listData.collect { pagingData ->
                println("collect içinde")
                movieAdapter.submitData(pagingData)
                println("adapter okudu kod bitti")
                println("data success")

            }
        }

    }


    private fun setUpRV() {
        println("rv coming")
        movieAdapter = PopularMovieAdapter()
        binding.recyclerView.apply {

            adapter = movieAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            println("rv showing")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}