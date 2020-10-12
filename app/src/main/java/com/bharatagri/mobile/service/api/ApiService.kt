package com.bharatagri.mobile.service.api

import com.bharatagri.mobile.BuildConfig
import com.bharatagri.mobile.service.modal.MovieDetailsResponse
import com.bharatagri.mobile.service.modal.MoviesResponse
import com.bharatagri.mobile.utils.Constants.LANGUAGE_CODE_US
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("language") language: String = LANGUAGE_CODE_US,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") pageNumber: Int
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = LANGUAGE_CODE_US
    ): Response<MovieDetailsResponse>
}