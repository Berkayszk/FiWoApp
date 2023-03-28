package com.example.fiwoapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.model.popularmovie.Result
import java.io.IOException

class PopularMovieSource(private val apiService: ApiService) : PagingSource<Int,com.example.fiwoapp.model.popularmovie.Result>(){

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?:1
            val popularMovie = apiService.getPopularMovie(nextPage)
            LoadResult.Page(
                data = popularMovie.body()!!.results,
                prevKey = if (nextPage==1) null else nextPage -1,
                nextKey = if (popularMovie.body()!!.results.isEmpty()) null else popularMovie.body()!!.page +1
            )
        }catch (exception :Exception){
            LoadResult.Error(exception)
        }catch (exception : Exception){
            return LoadResult.Error(exception)
        }
    }
}