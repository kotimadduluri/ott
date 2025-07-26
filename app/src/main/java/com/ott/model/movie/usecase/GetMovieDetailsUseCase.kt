package com.ott.model.movie.usecase

import com.ott.model.Resource
import com.ott.model.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//use-case to get movie details by respective id
class GetMovieDetailsUseCase : KoinComponent {
    private val repository: MovieRepository by inject()
    suspend operator fun invoke(movieId: Int): Resource {
        return repository.getMoviesDetails(movieId)
    }
}