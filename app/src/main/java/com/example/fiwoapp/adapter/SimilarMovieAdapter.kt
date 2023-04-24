package com.example.fiwoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.fiwoapp.databinding.PopularMovieRowBinding
import com.example.fiwoapp.model.similarmovie.Result
import com.example.fiwoapp.model.similarmovie.SimilarMovieResponse
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.util.Constants

class SimilarMovieAdapter(private val repository: MovieShowRepository) : PagingDataAdapter<com.example.fiwoapp.model.similarmovie.Result,SimilarMovieAdapter.SimilarMovieViewHolder>(
    diffCallBack) {
    class SimilarMovieViewHolder(val binding : PopularMovieRowBinding): ViewHolder(binding.root)

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
        holder.binding.apply {
            val imageLinkBackground = "${Constants.IMAGE_BASE_UR}${currentItem!!.poster_path}"
            imageViewPoster.load(imageLinkBackground){
                crossfade(1000)
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder(PopularMovieRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

}