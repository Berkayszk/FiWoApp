package com.example.fiwoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.fiwoapp.databinding.PopularTvRowBinding
import com.example.fiwoapp.model.similartv.Result
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.util.Constants

class SimilarTvAdapter: PagingDataAdapter<com.example.fiwoapp.model.similartv.Result, SimilarTvAdapter.SimilarTvViewHolder>(diffCallBack) {
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
            val imageLinkBackground = "${Constants.IMAGE_BASE_OR}${currentItem!!.backdrop_path}"
            imageViewPoster.load(imageLinkBackground) {
                crossfade(1000)
                crossfade(true)
            }
            movieName.text = currentItem.name

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