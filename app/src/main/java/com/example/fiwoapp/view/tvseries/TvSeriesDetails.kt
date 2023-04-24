package com.example.fiwoapp.view.tvseries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.fiwoapp.R
import com.example.fiwoapp.adapter.PopularTvAdapter
import com.example.fiwoapp.databinding.FragmentTvSeriesDetailsBinding
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.popular.MovieDetailsFragmentArgs
import com.example.fiwoapp.view.popular.tvseries.TvSeriesDetailsArgs
import com.example.fiwoapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvSeriesDetails : Fragment(R.layout.fragment_tv_series_details) {
    private var _binding: FragmentTvSeriesDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvAdapter: PopularTvAdapter
    private var tvId = 0
    private val args : TvSeriesDetailsArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvSeriesDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvId= args.tvId
        if (tvId>0){
            viewModel.loadDetailsTv(tvId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()

    }

    private fun showData() {
        binding.apply {
            viewModel.detailsTv.observe(viewLifecycleOwner) {
                val moviePosterUrlOrg = Constants.IMAGE_BASE_OR + it.backdrop_path
                val moviePosterUrl = Constants.IMAGE_BASE_UR + it.poster_path
                val studioPicture =
                    Constants.IMAGE_BASE_UR + it.networks[0].logo_path
                imageDetailOriginal.load(moviePosterUrlOrg) {
                    crossfade(true)
                    crossfade(1000)
                    scale(Scale.FILL)
                }
                imageViewPoster.load(moviePosterUrl) {
                    crossfade(1000)
                    crossfade(true)
                    scale(Scale.FILL)
                }
                studioImageView.load(studioPicture) {
                    crossfade(true)
                    crossfade(1000)
                    scale(Scale.FILL)
                }
                tagLineText.text = it.tagline
                movieName.text = it.name
                releaseDate.text = it.last_air_date
                //releaseDateInfo.text = "${"Revenue: "+ it.}"
                studioName.text = it.networks[0].name
                subjectText.text = it.overview
                runTimeText.text = "${it.seasons.get(0).season_number.toString() + " Seasons " + it.seasons.get(0).episode_count+" Episode"}"
                genreText.text = it.genres.get(0).name
                //countryText.text = it.production_countries
                voteText.text = it.vote_average.toString()
                voteTextAll.text = it.vote_count.toString()
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}