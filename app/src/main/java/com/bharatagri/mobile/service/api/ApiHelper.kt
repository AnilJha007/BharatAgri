package com.bharatagri.mobile.service.api

import com.bharatagri.mobile.service.modal.MovieDetails
import com.bharatagri.mobile.service.modal.MoviesResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getMovies(pageNumber: Int): Response<MoviesResponse>

    suspend fun getMovieDetails(movieId: Long): Response<MovieDetails>
}