package com.example.fiwoapp.viewmodel

import android.util.Log
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


    val loadingDetails = MutableLiveData<Boolean>()

    /* //Solution 1
    private var _loadingDetails = MutableLiveData<DetailsResponse>()
    val movieDetailsResponse : LiveData<DetailsResponse>
    get() = _loadingDetails

     */

    val moviesList = Pager(PagingConfig(1)) {
        PopularMovieSource(repository)
    }.flow.cachedIn(viewModelScope)

    val detailsMovie = MutableLiveData<DetailsResponse>()
    fun loadDetailsMovie(id : Int) = viewModelScope.launch {
        loadingDetails.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful){
            detailsMovie.postValue(response.body())
        }
        loadingDetails.postValue(false)
    }

    //Solution 1
    /*
    fun loadDetailsMovie(id:Int) = viewModelScope.launch {
        repository.getMovieDetails(id).let {
            if (it.isSuccessful){
                _loadingDetails.postValue(it.body())
            }else{
                Log.d("","getPeople: ${it.code()}")
            }
        }
    }
     */

}