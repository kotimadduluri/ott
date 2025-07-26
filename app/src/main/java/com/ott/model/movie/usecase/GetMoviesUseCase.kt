package com.ott.model.movie.usecase

import com.ott.model.Resource
import com.ott.model.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMoviesUseCase : KoinComponent {
    private val repository : MovieRepository by inject()
    suspend operator fun invoke(
        page:Int
    ): Resource {
        return repository.getMovies(page)
    }
}