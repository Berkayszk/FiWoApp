package com.example.fiwoapp.api

import com.example.fiwoapp.model.moviedetail.DetailsResponse
import com.example.fiwoapp.model.popularmovie.PopularResponse
import com.example.fiwoapp.model.popularpeople.PopularPeopleResponse
import com.example.fiwoapp.model.populartv.PopularTvResponse
import com.example.fiwoapp.model.similarmovie.SimilarMovieResponse
import com.example.fiwoapp.model.similartv.SimilarTvResponse
import com.example.fiwoapp.model.tvdetail.TvDetailResponse
import com.example.fiwoapp.util.Constants
import com.example.fiwoapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //https://api.themoviedb.org/3/movie/popular?api_key=87be3a25620bfec5b2c45e1fd566b362&language=en-US&page=1

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page : Int = Constants.STARTING_PAGE_INDEX

    ) : Response<PopularResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<DetailsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path("movie_id") movieId : Int,
        @Query("page") page : Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<SimilarMovieResponse>


    @GET("/3/tv/top_rated")
    suspend fun getTvSeries(
        @Query("api_key") apiKey : String = Constants.API_KEY,
        @Query("page") page : Int = Constants.STARTING_PAGE_INDEX
    ) : Response<PopularTvResponse>

    @GET("/3/tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId:Int,
        @Query("api_key") apiKey:String = Constants.API_KEY
    ) : Response<TvDetailResponse>

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTv(
        @Path("tv_id") tvId: Int,
        @Query("page") page : Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<SimilarTvResponse>


    @GET("/3/person/popular")
    suspend fun getPopularPeople(
        @Query("page") page : Int = Constants.STARTING_PAGE_INDEX,
        @Query("api_key") api_key: String = Constants.API_KEY
    ) : Response<PopularPeopleResponse>

    @GET("/3/person/{person_id}")
    suspend fun getPeopleDetails(
        @Path("person_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<TvDetailResponse>
}