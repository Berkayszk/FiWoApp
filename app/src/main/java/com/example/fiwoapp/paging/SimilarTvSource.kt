package com.example.fiwoapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fiwoapp.model.similartv.Result
import com.example.fiwoapp.repo.MovieShowRepository
import retrofit2.HttpException

class SimilarTvSource(private val repository: MovieShowRepository) : PagingSource<Int,com.example.fiwoapp.model.similartv.Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?:1
            val response = repository.getTvSimilar(6,currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<com.example.fiwoapp.model.similartv.Result>()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
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