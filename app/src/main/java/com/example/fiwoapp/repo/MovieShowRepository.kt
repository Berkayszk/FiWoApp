package com.example.fiwoapp.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.paging.PopularMovieSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieShowRepository @Inject constructor(val apiService : ApiService){
    fun getPopularMovies() : Flow<PagingData<com.example.fiwoapp.model.popularmovie.Result>>{
        return Pager(config = PagingConfig(enablePlaceholders = false, pageSize = 27),
        pagingSourceFactory = {
            PopularMovieSource(apiService)
        }).flow
    }
}