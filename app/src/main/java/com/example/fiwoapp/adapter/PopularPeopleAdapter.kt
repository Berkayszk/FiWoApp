package com.example.fiwoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.fiwoapp.databinding.PeopleRowBinding
import com.example.fiwoapp.model.popularpeople.Result
import com.example.fiwoapp.util.Constants

class PopularPeopleAdapter : PagingDataAdapter<com.example.fiwoapp.model.popularpeople.Result,PopularPeopleAdapter.PeopleViewHolder>(diffCallback) {

    class PeopleViewHolder(val binding : PeopleRowBinding) : ViewHolder(binding.root)

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<com.example.fiwoapp.model.popularpeople.Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem==newItem
            }

        }
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val currentPeople = getItem(position)
        holder.binding.apply {
            val imageLinkBackground = "${Constants.IMAGE_BASE_UR}${currentPeople!!.profile_path}"
            imageViewPoster.load(imageLinkBackground){
                crossfade(1000)
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(PeopleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}