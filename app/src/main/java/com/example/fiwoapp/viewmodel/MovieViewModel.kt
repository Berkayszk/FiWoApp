package com.example.fiwoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.model.moviedetail.DetailsResponse
import com.example.fiwoapp.model.popularmovie.PopularResponse
import com.example.fiwoapp.model.popularmovie.Result
import com.example.fiwoapp.paging.PopularMovieSource
import com.example.fiwoapp.repo.MovieShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieShowRepository,
    private val apiService: ApiService
) : ViewModel() {


    val loading = MutableLiveData<Boolean>()

    val moviesList = Pager(PagingConfig(1)) {
        PopularMovieSource(repository)
    }.flow.cachedIn(viewModelScope)

    //Api
    val detailsMovie = MutableLiveData<DetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }
}