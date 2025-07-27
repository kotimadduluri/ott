package com.ott.data.movie.usecase

import com.ott.data.Resource
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.data.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMoviesUseCase(val repository: MovieRepository) : KoinComponent {
    suspend operator fun invoke(page: Int): Resource<MoviesResponse> {
        return repository.getMovies(page)
    }
}