package com.example.fiwoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.example.fiwoapp.databinding.FragmentMovieBinding
import com.example.fiwoapp.databinding.PopularMovieRowBinding
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.popular.MovieFragmentDirections

class
PopularMovieAdapter(private val context : Context) : PagingDataAdapter<com.example.fiwoapp.model.popularmovie.Result,PopularMovieAdapter.PopularMovieHolder>(
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
        if(currentItem!=null) {
            holder.binding.apply {
                movieName.text = currentItem!!.title
                val imageLink = "${Constants.IMAGE_BASE_UR}${currentItem.poster_path}"
                val imageLinkBackground = "${Constants.IMAGE_BASE_UR}${currentItem.backdrop_path}"
                imageView.load(imageLink) {
                    crossfade(true)
                    crossfade(1000)

                }
                imageViewPoster.load(imageLinkBackground) {
                    crossfade(true)
                    crossfade(1000)

                }
                holder.itemView.setOnClickListener {
                    val direction =
                        MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(currentItem.id)
                    it.findNavController().navigate(direction)

                    println("PopularMovieAdapter Clicked movie id: ${currentItem?.id}")
                }
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

            }
        }
        else
        {
            Toast.makeText(context, "The Movie is not uploaded!!", Toast.LENGTH_SHORT).show()
        }


    }

}