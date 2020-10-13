package com.bharatagri.mobile.service.modal

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieDetailsResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    val budget: Int,
    val genres: List<Genres>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguages>,
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

@Parcelize
data class BelongsToCollection(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
) : Parcelable

data class ProductionCompanies(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)

data class ProductionCountries(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @SerializedName("name")
    val name: String
)

data class SpokenLanguages(
    @SerializedName("iso_639_1")
    val iso_639_1: String,
    @SerializedName("name")
    val name: String
)

data class Genres(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)