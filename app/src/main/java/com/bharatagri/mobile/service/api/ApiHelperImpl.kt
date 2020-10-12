package com.bharatagri.mobile.service.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMovies(pageNumber: Int) = apiService.getMovies(pageNumber = pageNumber)

    override suspend fun getMovieDetails(movieId: Long) =
        apiService.getMovieDetails(movieId = movieId)
}