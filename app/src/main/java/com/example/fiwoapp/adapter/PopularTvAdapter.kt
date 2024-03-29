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
import com.example.fiwoapp.databinding.PopularTvRowBinding
import com.example.fiwoapp.model.populartv.Result
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.popular.MovieFragmentDirections

class PopularTvAdapter(private val context: Context) : PagingDataAdapter<com.example.fiwoapp.model.populartv.Result, PopularTvAdapter.PopularTvHolder>(diffCallback) {

    private val movieId : Int = 0

    class PopularTvHolder(val binding : PopularTvRowBinding) : ViewHolder(binding.root)

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<com.example.fiwoapp.model.populartv.Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return  oldItem==newItem
            }

        }

    }

    override fun onBindViewHolder(holder: PopularTvHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            movieName.text = currentItem!!.name
            val imageLink = "${Constants.IMAGE_BASE_UR}${currentItem.poster_path}"
            val imageLinkBackground = "${Constants.IMAGE_BASE_UR}${currentItem.backdrop_path}"
            /*
            imageView.load(imageLink){
                crossfade(1000)
                crossfade(true)
            }

             */
            imageViewPoster.load(imageLink){
                crossfade(1000)
                crossfade(true)
            }
        }
        holder.itemView.setOnClickListener {
            val direction = MovieFragmentDirections.actionMovieFragmentToTvSeriesDetails(currentItem!!.id)
            it.findNavController().navigate(direction)
        }
        holder.binding.cbHeart.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked)
            {
                println("checked")
                Toast.makeText(context,"The Movie Added Favorite..", Toast.LENGTH_SHORT).show()
            }
            else{
                println("checked else")
                Toast.makeText(context,"The Movie Removed Favorite..", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvAdapter.PopularTvHolder {
        return PopularTvHolder(PopularTvRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

}