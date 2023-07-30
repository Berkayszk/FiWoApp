package com.example.fiwoapp.view.popular

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiwoapp.R
import com.example.fiwoapp.adapter.PopularMovieAdapter
import com.example.fiwoapp.adapter.PopularPeopleAdapter
import com.example.fiwoapp.adapter.PopularTvAdapter
import com.example.fiwoapp.databinding.FragmentMovieBinding
import com.example.fiwoapp.databinding.FragmentPeopleBinding
import com.example.fiwoapp.databinding.PeopleRowBinding
import com.example.fiwoapp.databinding.PopularTvRowBinding
import com.example.fiwoapp.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Console


@AndroidEntryPoint
class MovieFragment() : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var _bindingFav: PopularTvRowBinding? = null
    private val bindingFav get() = _bindingFav!!

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: PopularMovieAdapter
    private lateinit var tvAdapter : PopularTvAdapter
    private lateinit var peopleAdapter : PopularPeopleAdapter
    private var movieLoaded = false
    private var tvLoaded = false
    private var peopleLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        _bindingFav = PopularTvRowBinding.inflate(layoutInflater,container,false)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpRv()
        loadingData()



    }
    private fun checkDataLoaded() {
        val allDataLoaded = movieAdapter.itemCount > 0 && tvAdapter.itemCount > 0 && peopleAdapter.itemCount > 0
        if (allDataLoaded) {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.popularMovieRv.visibility = View.VISIBLE
            binding.popularTvRv.visibility = View.VISIBLE
            binding.popularPeople.visibility = View.VISIBLE
        }
    }

    private fun loadingData() {
        lifecycleScope.launch {
            viewModel.moviesList.collect { pagingData ->
                movieAdapter.submitData(pagingData)
                checkDataLoaded()
            }
        }
        lifecycleScope.launch {
            viewModel.tvList.collect { pagingTv ->
                tvAdapter.submitData(pagingTv)
                checkDataLoaded()
            }
        }

        lifecycleScope.launch {
            viewModel.peopleList.collect { pagingPeople ->
                peopleAdapter.submitData(pagingPeople)
                checkDataLoaded()

            }
        }

    }





    private fun setUpRv(){

        movieAdapter = PopularMovieAdapter(requireContext())
        tvAdapter = PopularTvAdapter(requireContext())
        peopleAdapter = PopularPeopleAdapter()
        binding.popularMovieRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
        binding.popularTvRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = tvAdapter
            setHasFixedSize(true)
        }
        binding.popularPeople.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = peopleAdapter
            setHasFixedSize(true)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



/*
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: PopularMovieAdapter
    private lateinit var tvAdapter : PopularTvAdapter
    private lateinit var peopleAdapter : PopularPeopleAdapter
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

        movieAdapter = PopularMovieAdapter()
        tvAdapter = PopularTvAdapter()
        peopleAdapter = PopularPeopleAdapter()
        setUpRv()
        loadingData()


    }


    private fun loadingData() {

        lifecycleScope.launch {
            viewModel.tvList.collect{pagingTv->
                tvAdapter.submitData(pagingTv)
            }
        }
        lifecycleScope.launch {
            viewModel.peopleList.collect{pagingPeople->
                peopleAdapter.submitData(pagingPeople)
            }
        }
    }
    private fun setUpRv(){

        binding.popularMovieRv.apply {

            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
        binding.popularTvRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = tvAdapter
            setHasFixedSize(true)
        }
        binding.popularPeople.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = peopleAdapter
            setHasFixedSize(true)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

 */