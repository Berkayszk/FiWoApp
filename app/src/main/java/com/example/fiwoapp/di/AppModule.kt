package com.example.fiwoapp.di

import com.example.fiwoapp.api.ApiService
import com.example.fiwoapp.repo.MovieShowRepository
import com.example.fiwoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideImageBaseUrl() = Constants.IMAGE_BASE_UR

    @Provides
    @Singleton
    fun provideRetrofitInstance() : ApiService=
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePopularMovieRepository(apiService : ApiService) = MovieShowRepository(apiService)

}