package com.example.fiwoapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fiwoapp.model.popularmovie.Result
import com.example.fiwoapp.repo.MovieShowRepository
import retrofit2.HttpException

class PopularTvSeriesSource(private val repository: MovieShowRepository) : PagingSource<Int,com.example.fiwoapp.model.populartv.Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.fiwoapp.model.populartv.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getPopularTv(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<com.example.fiwoapp.model.populartv.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, com.example.fiwoapp.model.populartv.Result>): Int? {
        return null
    }
}