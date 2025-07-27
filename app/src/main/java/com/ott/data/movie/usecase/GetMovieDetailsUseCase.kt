package com.ott.data.movie.usecase

import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent

//use-case to get movie details by respective id
class GetMovieDetailsUseCase(val repository: MovieRepository) : KoinComponent {
    suspend operator fun invoke(movieId: Int): Resource<MovieDetailsResponse> {
        return repository.getMoviesDetails(movieId)
    }
}