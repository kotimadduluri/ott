package com.ott.ui.movie.list

import androidx.lifecycle.viewModelScope
import com.ott.data.Resource
import com.ott.data.movie.data.getMovies
import com.ott.data.movie.usecase.GetMoviesUseCase
import com.ott.common.BaseViewModel
import com.ott.core_ui.util.UiText
import com.ott.data.movie.data.domain.model.Movie
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesUseCase: GetMoviesUseCase,
) : BaseViewModel<UiState<List<Movie>>>(UiState.None) {

    private val currentPage = MutableStateFlow(1)

    init {
        onAction(MoviesScreenIntent.GetMovies(currentPage.value))
    }

    fun onAction(intent: MoviesScreenIntent, navigate: (route: String) -> Unit = {}) {
        when (intent) {
            is MoviesScreenIntent.Refresh -> {
                getMovies(1) //to start from page 1
            }

            is MoviesScreenIntent.GetMovies -> {
                getMovies(intent.page)
            }

            is MoviesScreenIntent.ViewDetails -> {
                //navigate(MovieModule.Details.createRoute(intent.movie.id))
            }
        }
    }

    fun getMovies(page: Int) {
        emitData(UiState.Loading)
        viewModelScope.launch {
            val response = moviesUseCase(page)
            when (response) {
                is Resource.Loading -> {
                    emitData(UiState.Loading)
                }

                is Resource.Error -> {
                    emitData(UiState.Error(response.message))
                }

                is Resource.Success -> {
                    handleSuccessResponse(response.data, page)
                }
            }
        }
    }

    private fun handleSuccessResponse(response: MoviesResponse?, page: Int) {
        with(response?.getMovies()) {
            if (isNullOrEmpty()) {
                emitData(UiState.Error(UiText.PlainString("No movies found")))
            } else {
                currentPage.value = page
                emitData(UiState.Success(this))
            }
        }
    }
}

sealed class MoviesScreenIntent {
    data class GetMovies(val page: Int) : MoviesScreenIntent()
    data class ViewDetails(val movie: Movie) : MoviesScreenIntent()
    data object Refresh : MoviesScreenIntent()
}