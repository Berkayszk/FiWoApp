package com.example.fiwoapp.view.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.fiwoapp.adapter.SimilarMovieAdapter
import com.example.fiwoapp.databinding.FragmentMovieDetailsBinding
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding : FragmentMovieDetailsBinding?=null
    private val binding get() = _binding!!
    private var movieId = 0
    private val viewModel: MovieViewModel by viewModels()
    private val args : MovieDetailsFragmentArgs by navArgs()
    private lateinit var similarMovieAdapter : SimilarMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId>0){
            viewModel.loadDetailsMovie(movieId)
        }
        viewModel.setMovieId(movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        similarMovieAdapter = SimilarMovieAdapter(requireContext())
        similarMovieRv()
        loadingSimilarData()
        showData()


    }
    private fun showData(){
        binding.apply {
            viewModel.detailsMovie.observe(viewLifecycleOwner) {
                val moviePosterUrlOrg = Constants.IMAGE_BASE_OR + it.backdrop_path
                val moviePosterUrl = Constants.IMAGE_BASE_UR+it.poster_path
                val studioPicture = Constants.IMAGE_BASE_UR+it.production_companies.get(0).logo_path
                imageDetailOriginal.load(moviePosterUrlOrg){
                    crossfade(true)
                    crossfade(1000)
                    scale(Scale.FILL)
                }
                imageViewPoster.load(moviePosterUrl){
                    crossfade(1000)
                    crossfade(true)
                    scale(Scale.FILL)
                }
                studioImageView.load(studioPicture){
                    crossfade(true)
                    crossfade(1000)
                    scale(Scale.FILL)
                }
                tagLineText.text = it.tagline
                movieName.text = it.title
                releaseDate.text = "${"Release Date: "+ it.release_date + " ," +it.production_countries.get(0).name}"
                releaseDateInfo.text = "${"Revenue: "+ it.revenue.toString()}"
                studioName.text = it.production_companies.get(0).name
                subjectText.text = it.overview
                runTimeText.text = "${it.runtime.toString() +  "min"}"
                genreText.text = it.genres.get(0).name
                countryText.text = "${"Made "+ it.production_countries.get(0).name}"
                voteText.text = "${"IMDB: "+ it.vote_average.toString()}"
                voteTextAll.text = "${"Vote: "+it.vote_count.toString()}"

            }

        }
    }

    private fun similarMovieRv(){
            binding.similarMovieRv.apply {
            adapter = similarMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }
    private fun loadingSimilarData(){
        lifecycleScope.launch {
            viewModel.movieSimilarList.collect{pagingData->
                similarMovieAdapter.submitData(pagingData)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}