package com.ott.ui.viewmodel.movie

import androidx.lifecycle.viewModelScope
import com.ott.model.Resource
import com.moviemax.model.movie.data.getMovies
import com.ott.model.movie.usecase.GetMoviesUseCase
import com.ott.common.BaseViewModel
import com.ott.core_ui.util.UiText
import com.ott.model.movie.data.domain.model.Movie
import com.ott.model.movie.data.remote.model.MoviesResponse
import com.ott.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesScreenViewModel(
    private val moviesUseCase: GetMoviesUseCase,
) : BaseViewModel<UiState>(UiState.None) {

    private val currentPage = MutableStateFlow(1)

    init {
        onAction(MoviesScreenIntent.GetMovies(currentPage.value))
    }

    fun onAction(intent: MoviesScreenIntent, navigate:(route:String)->Unit ={}) {
        when (intent) {
            is MoviesScreenIntent.Refresh -> {
                getMovies(1) //to start from page 1
            }

            is MoviesScreenIntent.GetMovies -> {
                getMovies(intent.page)
            }

            is MoviesScreenIntent.ViewDetails ->{
                //navigate(MovieModule.Details.createRoute(intent.movie.id))
            }
        }
    }

    fun getMovies(page: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val networkResponse = moviesUseCase(page)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse as Resource.Success<MoviesResponse>
                with(response.data?.getMovies()) {
                    if (isNullOrEmpty()) {
                        uiState.value = UiState.Error(UiText.PlainString("No movies found"))
                    } else {
                        currentPage.value = page
                        uiState.value = UiState.Success(this)
                    }
                }
            } else {
                uiState.value = UiState.Error(networkResponse.message)
            }
        }
    }
}

sealed class MoviesScreenIntent {
    data class GetMovies(val page: Int) : MoviesScreenIntent()
    data class ViewDetails(val movie: Movie) : MoviesScreenIntent()
    data object Refresh : MoviesScreenIntent()
}