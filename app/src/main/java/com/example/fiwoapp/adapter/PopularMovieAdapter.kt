package com.example.fiwoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.example.fiwoapp.databinding.FragmentMovieBinding
import com.example.fiwoapp.databinding.PopularMovieRowBinding
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.MovieFragmentDirections

class PopularMovieAdapter : PagingDataAdapter<com.example.fiwoapp.model.popularmovie.Result,PopularMovieAdapter.PopularMovieHolder>(
    diffCallBack) {
    class PopularMovieHolder(val binding : PopularMovieRowBinding) : ViewHolder(binding.root)

    companion object {
     val diffCallBack = object : DiffUtil.ItemCallback<com.example.fiwoapp.model.popularmovie.Result>(){
        override fun areItemsTheSame(oldItem: com.example.fiwoapp.model.popularmovie.Result, newItem: com.example.fiwoapp.model.popularmovie.Result): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: com.example.fiwoapp.model.popularmovie.Result, newItem: com.example.fiwoapp.model.popularmovie.Result): Boolean {
            return oldItem == newItem
        }
    }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieHolder {
        return PopularMovieHolder(PopularMovieRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMovieHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            movieName.text = currentItem!!.title
            val imageLink = "${Constants.IMAGE_BASE_UR}${currentItem.poster_path}"
            val imageLinkBackground = "${Constants.IMAGE_BASE_UR}${currentItem.backdrop_path}"
            imageView.load(imageLink){
                crossfade(true)
                crossfade(1000)

            }
            imageViewPoster.load(imageLinkBackground){
                crossfade(true)
                crossfade(1000)

            }

        }
        holder.itemView.setOnClickListener {
            val direction = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(currentItem!!.id)
            it.findNavController().navigate(direction)
        }
    }
}