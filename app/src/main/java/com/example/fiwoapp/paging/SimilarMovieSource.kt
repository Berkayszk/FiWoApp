package com.example.fiwoapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fiwoapp.model.similarmovie.Result
import com.example.fiwoapp.repo.MovieShowRepository
import retrofit2.HttpException

class SimilarMovieSource(private val repository: MovieShowRepository, private val movieId:Int) : PagingSource<Int,com.example.fiwoapp.model.similarmovie.Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?:1
            val response = repository.getMovieSimilar(movieId,currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<com.example.fiwoapp.model.similarmovie.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data=responseData,
                prevKey = if (currentPage==1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}