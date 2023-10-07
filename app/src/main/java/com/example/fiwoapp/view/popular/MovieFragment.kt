package com.example.fiwoapp.view.popular

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiwoapp.R
import com.example.fiwoapp.adapter.PopularMovieAdapter
import com.example.fiwoapp.adapter.PopularPeopleAdapter
import com.example.fiwoapp.adapter.PopularTvAdapter
import com.example.fiwoapp.databinding.FragmentMovieBinding
import com.example.fiwoapp.databinding.PopularTvRowBinding
import com.example.fiwoapp.repo.NetworkCallbackImplement
import com.example.fiwoapp.viewmodel.MovieViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch




@AndroidEntryPoint
class MovieFragment() : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var _bindingFav: PopularTvRowBinding? = null
    private val bindingFav get() = _bindingFav!!

    private lateinit var shimmerLayout: ShimmerFrameLayout
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: PopularMovieAdapter
    private lateinit var tvAdapter : PopularTvAdapter
    private lateinit var peopleAdapter : PopularPeopleAdapter
    private var movieLoaded = false
    private var tvLoaded = false
    private var peopleLoaded = false
    private val networkCallbackImplement = NetworkCallbackImplement(
        onNetworkAvailable = {
            loadingData()
        },
        onNetworkLost = {
            Toast.makeText(requireContext(), "Your internet connection has been lost.", Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        _bindingFav = PopularTvRowBinding.inflate(layoutInflater,container,false)
        shimmerLayout = _binding!!.shimmerLayout

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()

        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build(),
            networkCallbackImplement
        )
        registerNetworkCallback()

    }
    private fun loadingData() {
        lifecycleScope.launch {
            viewModel.moviesList.collect { pagingData ->
                movieAdapter.submitData(pagingData)

            }
        }

        lifecycleScope.launch {
            viewModel.tvList.collect { pagingTv ->
                tvAdapter.submitData(pagingTv)

            }
        }

        lifecycleScope.launch {
            viewModel.peopleList.collect { pagingPeople ->
                peopleAdapter.submitData(pagingPeople)

            }
        }


        movieAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.NotLoading) {
                movieLoaded = true
                checkDataLoaded()
            }
        }

        tvAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.NotLoading) {
                tvLoaded = true
                checkDataLoaded()
            }
        }

        peopleAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.NotLoading) {
                peopleLoaded = true
                checkDataLoaded()
            }
        }
    }
    private fun checkDataLoaded() {
        val allDataLoaded = movieLoaded && tvLoaded && peopleLoaded //Check data.
        if (allDataLoaded) {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            binding.trendMoviesText.visibility = View.VISIBLE
            binding.popularTvSeriesTv.visibility = View.VISIBLE
            binding.actorsTv.visibility = View.VISIBLE

        }
    }
    private fun registerNetworkCallback() {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallbackImplement)
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
