package com.bharatagri.mobile.service.repository

import com.bharatagri.mobile.service.database.dao.MovieDao
import com.bharatagri.mobile.service.modal.Movie
import com.bharatagri.mobile.service.modal.MovieDetails
import javax.inject.Inject

class LocalRepository @Inject constructor(private val movieDao: MovieDao) {

    suspend fun insertAllMovies(movies: MutableList<Movie>) =
        movieDao.insertAllMovies(movies)

    suspend fun insertMovieDetails(movie: MovieDetails) =
        movieDao.insertMovieDetails(movie)

    fun fetchMovies() = movieDao.getAllMovies()

    fun fetchMovieDetails(movieId: Long) = movieDao.getMovieDetailsById(movieId)

    suspend fun deleteAllMovies() = movieDao.deleteAllMovies()

    suspend fun deleteAllMovieDetails() = movieDao.deleteAllMovieDetails()
}