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
import com.example.fiwoapp.model.similartv.Result
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.view.tvseries.TvSeriesDetails
import com.example.fiwoapp.view.tvseries.TvSeriesDetailsDirections

class SimilarTvAdapter(private val context: Context): PagingDataAdapter<com.example.fiwoapp.model.similartv.Result, SimilarTvAdapter.SimilarTvViewHolder>(diffCallBack) {
    class SimilarTvViewHolder(val binding: PopularTvRowBinding) : ViewHolder(binding.root)

    companion object {
        val diffCallBack =
            object : DiffUtil.ItemCallback<com.example.fiwoapp.model.similartv.Result>() {
                override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem == newItem
                }

            }
    }

    override fun onBindViewHolder(holder: SimilarTvViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            val imageLinkBackground = "${Constants.IMAGE_BASE_OR}${currentItem?.poster_path}"
            imageViewPoster.load(imageLinkBackground) {
                crossfade(1000)
                crossfade(true)
            }
            movieName.text = currentItem?.name

        }
        holder.itemView.setOnClickListener {
            val direction = TvSeriesDetailsDirections.actionTvSeriesDetailsSelf(currentItem!!.id)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarTvViewHolder {
            return SimilarTvViewHolder(
                PopularTvRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

}