package com.ott.data.movie.usecase

import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//use-case to get movie details by respective id
class GetMovieDetailsUseCase : KoinComponent {
    private val repository: MovieRepository by inject()
    suspend operator fun invoke(movieId: Int): Resource<MovieDetailsResponse> {
        return repository.getMoviesDetails(movieId)
    }
}