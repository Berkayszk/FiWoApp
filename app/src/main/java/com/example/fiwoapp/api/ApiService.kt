package com.example.fiwoapp.api

import com.example.fiwoapp.model.moviedetail.DetailsResponse
import com.example.fiwoapp.model.popularmovie.PopularResponse
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //https://api.themoviedb.org/3/movie/popular?api_key=87be3a25620bfec5b2c45e1fd566b362&language=en-US&page=1

    @GET("/3/movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page : Int = Constants.STARTING_PAGE_INDEX,
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language : String = "en"
    ) : Response<PopularResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): DetailsResponse


}