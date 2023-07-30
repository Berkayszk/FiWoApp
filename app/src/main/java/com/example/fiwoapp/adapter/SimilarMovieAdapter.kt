package com.example.fiwoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.fiwoapp.databinding.PopularMovieRowBinding
import com.example.fiwoapp.databinding.PopularTvRowBinding
import com.example.fiwoapp.model.similarmovie.Result
import com.example.fiwoapp.model.similarmovie.SimilarMovieResponse
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.popular.MovieDetailsFragmentDirections
import com.example.fiwoapp.view.tvseries.TvSeriesDetailsDirections

class SimilarMovieAdapter(private val context: Context) : PagingDataAdapter<Result,SimilarMovieAdapter.SimilarMovieViewHolder>(diffCallBack) {
    class SimilarMovieViewHolder(val binding : PopularTvRowBinding): ViewHolder(binding.root)

    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<com.example.fiwoapp.model.similarmovie.Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem==newItem
            }

        }
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        println("currenItemüste similar movie adapter")
        if (currentItem!=null) {
            holder.binding.apply {
                val imageLinkBackground = "${Constants.IMAGE_BASE_OR}${currentItem?.poster_path}"
                imageViewPoster.load(imageLinkBackground) {
                    crossfade(1000)
                    crossfade(true)
                }
                movieName.text = currentItem?.title

                holder.binding.cbHeart.setOnCheckedChangeListener { checkBox, isChecked ->

                    if (isChecked) {
                        println("checked")
                        Toast.makeText(context, "The Movie Added Favorite..", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        println("checked else")
                        Toast.makeText(context, "The Movie Removed Favorite..", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                holder.itemView.setOnClickListener {
                    val direction =
                        MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(currentItem!!.id)
                    it.findNavController().navigate(direction)

                    println("PopularMovieAdapter Clicked movie id: ${currentItem?.id}")
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder(PopularTvRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

}