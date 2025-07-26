package com.ott.model.movie.repository

import com.ott.model.Resource
import com.ott.model.movie.MovieApi
import com.ott.core_ui.util.UiText
import com.ott.network.reader.NetworkReader

class MovieRepositoryImp(
    private val movieApi: MovieApi,
    private val networkReader: NetworkReader
) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource {
        return try {
            if (networkReader.isInternetAvailable()) {
                val response = movieApi.getMovies(page)
                Resource.Success(response)
            } else Resource.Error(message = UiText.PlainString("Network Error"))
        } catch (e: Exception) {
            Resource.Error(message = UiText.PlainString(e.localizedMessage))
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Resource {
        return try {
            if (networkReader.isInternetAvailable()) {
                Resource.Success(movieApi.getMovieDetails(movieId))
            } else Resource.Error(message = UiText.PlainString("Network Error"))
        } catch (e: Exception) {
            Resource.Error(message = UiText.PlainString(e.localizedMessage))
        }
    }
}