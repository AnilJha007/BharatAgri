package com.bharatagri.mobile.service.database

import com.bharatagri.mobile.service.database.dao.MovieDao
import com.bharatagri.mobile.service.modal.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: MovieDao) {

    suspend fun insertAllMovies(movies: MutableList<Movie>) =
        movieDao.insertAllMovies(movies)

    fun fetchMovies() = movieDao.getAllMovies()

    suspend fun deleteAllMovies() = movieDao.deleteAllMovies()
}