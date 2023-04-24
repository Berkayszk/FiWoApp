package com.example.fiwoapp.repo

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.paging.PopularMovieSource
import com.example.fiwoapp.util.Constants.API_KEY
import com.example.fiwoapp.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieShowRepository @Inject constructor(val apiService : ApiService){

    /* //Flow denemesi yapılmadı!!
      fun getPopularMovie() : Flow<PagingData<com.example.fiwoapp.model.view.Result>>{
        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {PopularMovieSource(apiService)}).flow
    }
     suspend fun getPopularMovieItem() = apiService.getPopularMovie(1)

    companion object{
        const val NETWORK_PAGE_SIZE = 27
    }
     */


   suspend fun getPopularMovie(page : Int) = apiService.getPopularMovie(API_KEY,page)
   suspend fun getMovieDetails(movieId:Int) = apiService.getMovieDetails(movieId)

   suspend fun getPopularTv(page: Int) = apiService.getTvSeries(API_KEY,page)
   suspend fun getPopularTvDetails(tvId : Int) = apiService.getTvDetails(tvId)

   suspend fun getPopularPeople(page : Int) = apiService.getPopularPeople(page)

   suspend fun getMovieSimilar(page: Int) = apiService.getMovieSimilar(page)
   suspend fun getTvSimilar(page: Int) = apiService.getSimilarTv(page)

}