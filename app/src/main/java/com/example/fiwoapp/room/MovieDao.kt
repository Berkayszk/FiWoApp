package com.example.fiwoapp.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.fiwoapp.entity.MovieEntity
import com.example.fiwoapp.entity.TrailerEntity
import com.example.fiwoapp.entity.TvShowEntity

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShow(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM movieentity WHERE favorite = 1 ")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentity WHERE favorite = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM movieentity WHERE id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentity WHERE id = :id")
    fun getTvShowById(id: Int): LiveData<TvShowEntity>

    @Transaction
    @Query("SELECT * FROM trailerentity WHERE id = :id")
    fun getTrailer(id: Int): LiveData<TrailerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrailer(Id: TrailerEntity)

}