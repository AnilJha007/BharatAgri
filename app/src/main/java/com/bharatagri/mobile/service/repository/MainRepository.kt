package com.bharatagri.mobile.service.repository

import com.bharatagri.mobile.service.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getMovies(pageNumber: Int) = apiHelper.getMovies(pageNumber)

    suspend fun getMovieDetails(movieId: Long) = apiHelper.getMovieDetails(movieId)
}