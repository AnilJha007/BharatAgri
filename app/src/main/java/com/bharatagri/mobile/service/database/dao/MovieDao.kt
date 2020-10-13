package com.bharatagri.mobile.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bharatagri.mobile.service.modal.Movie
import com.bharatagri.mobile.service.modal.MovieDetails

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: MutableList<Movie>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails): Long

    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): LiveData<MutableList<Movie>>

    @Query("SELECT * FROM movie_details where id = :movieId")
    fun getMovieDetailsById(movieId: Long): LiveData<MovieDetails>

    @Query("DELETE FROM movie_list")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movie_details")
    suspend fun deleteAllMovieDetails()
}