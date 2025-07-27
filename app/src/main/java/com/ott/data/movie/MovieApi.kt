package com.ott.data.movie

import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.data.remote.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/api/most-popular")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET("api/show-details")
    suspend fun getMovieDetails(
        @Query("q") movieId: Int
    ): MovieDetailsResponse
}