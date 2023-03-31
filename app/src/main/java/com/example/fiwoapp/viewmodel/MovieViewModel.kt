package com.example.fiwoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.model.popularmovie.PopularResponse
import com.example.fiwoapp.model.popularmovie.Result
import com.example.fiwoapp.paging.PopularMovieSource
import com.example.fiwoapp.repo.MovieShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieShowRepository, private val apiService: ApiService) : ViewModel() {

    //val getPopularMovie = repository.getPopularMovies().cachedIn(viewModelScope)
    /*
    private val _response = MutableLiveData<PopularResponse>()
    val movieResponse : LiveData<PopularResponse>
    get() = _response
    */
    /*
    fun getPopularMovies() : Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                PopularMovieSource(apiService)
            }).flow.cachedIn(viewModelScope)
    }

        */
        //val getPopularMovies = repository.getPopularMovie().cachedIn(viewModelScope)

        val listData = Pager(PagingConfig(pageSize = 1)){
            PopularMovieSource(apiService)
        }.flow.cachedIn(viewModelScope)
}