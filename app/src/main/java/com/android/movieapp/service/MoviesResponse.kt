package com.android.movieapp.service

import com.android.movieapp.model.MovieDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("Search") val searches: List<MovieDetail>,
    @SerialName("totalResults") val totalResults: String,
    @SerialName("Response") val response: String,
)

@Serializable
data class MovieResponse(
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("Rated") val rated: String,
    @SerialName("Released") val released: String,
    @SerialName("Runtime") val runtime: String,
    @SerialName("Genre") val genre: String,
    @SerialName("Director") val director: String,
    @SerialName("Writer") val writer: String,
    @SerialName("Actors") val actors: String,
    @SerialName("Plot") val plot: String,
    @SerialName("Language") val language: String,
    @SerialName("Country") val country: String,
    @SerialName("Awards") val awards: String,
    @SerialName("Poster") val poster: String,
    @SerialName("Metascore") val metaScore: String,
    @SerialName("imdbRating") val imdbRating: String,
    @SerialName("imdbVotes") val imdbVotes: String,
    @SerialName("imdbID") val imdbID: String,
    @SerialName("Type") val type: String,
    @SerialName("DVD") val dvd: String,
    @SerialName("BoxOffice") val boxOffice: String,
    @SerialName("Production") val production: String,
    @SerialName("Website") val website: String,
    @SerialName("Response") val response: String,
)