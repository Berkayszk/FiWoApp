package com.example.fiwoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadingData()
        setUpRv()
    }


    private fun loadingData() {

            movieAdapter = PopularMovieAdapter()
            lifecycleScope.launch {
                viewModel.moviesList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)

                }
            }
    }
    private fun setUpRv(){
        movieAdapter = PopularMovieAdapter()
        binding.recyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = movieAdapter
            setHasFixedSize(true)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}