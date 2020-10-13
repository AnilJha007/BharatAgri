package com.bharatagri.mobile.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bharatagri.mobile.service.modal.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(busStops: MutableList<Movie>): List<Long>

    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): LiveData<MutableList<Movie>>

    @Query("DELETE FROM movie_list")
    suspend fun deleteAllMovies()
}