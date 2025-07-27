package com.ott.data.movie.repository

import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.data.remote.model.MoviesResponse

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource<MoviesResponse>
    suspend fun getMoviesDetails(movieId: Int): Resource<MovieDetailsResponse>
}