package com.android.movieapp.repo

import com.android.movieapp.service.MovieService

class MovieRepo(
    private val movieService: MovieService = MovieService.create()
) {
    suspend fun getMovies(apiKey: String, query: String, page: Int = 1) = movieService.getMovies(apiKey, query, page)

    suspend fun getMovieDetails(apiKey: String, id: String) = movieService.getMovieDetails(apiKey, id)
}