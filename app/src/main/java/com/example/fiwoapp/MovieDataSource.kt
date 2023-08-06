package com.example.fiwoapp

import androidx.lifecycle.LiveData
import com.example.fiwoapp.entity.TrailerEntity
import com.example.fiwoapp.vo.Resource

interface MovieDataSource {
    fun getTrailerVideo(movieId: Int): LiveData<Resource<TrailerEntity>>

}