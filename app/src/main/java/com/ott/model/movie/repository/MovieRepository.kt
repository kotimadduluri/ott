package com.ott.model.movie.repository

import com.ott.model.Resource

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource
    suspend fun getMoviesDetails(movieId: Int): Resource
}