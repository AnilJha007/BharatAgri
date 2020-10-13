package com.bharatagri.mobile.service.modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResponse(
    val results: ArrayList<Movie>,
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    val dates: Dates,
    @SerializedName("total_pages")
    val totalPages: Int
) : Parcelable

@Entity(tableName = "movie_list")
@Parcelize
data class Movie(
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    @SerializedName("poster_path")
    val posterPath: String,
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("genre_ids")
    val genreIds: ArrayList<Int>,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String
) : Parcelable

@Parcelize
data class Dates(val maximum: String, val minimum: String) : Parcelable