package com.ott.ui.viewmodel.movie

import androidx.lifecycle.viewModelScope
import com.ott.model.Resource
import com.ott.model.asSuccess
import com.moviemax.model.movie.data.toMovie
import com.ott.model.movie.usecase.GetMovieDetailsUseCase
import com.ott.common.BaseViewModel
import com.ott.core_ui.util.UiText
import com.ott.model.movie.data.remote.model.MovieDetailsResponse
import com.ott.ui.UiState
import kotlinx.coroutines.launch

class MovieDetailsScreenViewModel(
    private val movieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<UiState>(UiState.None) {


    fun onAction(intent: MovieDetailsScreenIntent) {
        when (intent) {
            is MovieDetailsScreenIntent.GetDetails -> {
                getMovieDetails(intent.movieId)
            }
            is MovieDetailsScreenIntent.Refresh -> {
                getMovieDetails(intent.movieId)
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val networkResponse = movieDetailsUseCase(movieId)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse.asSuccess<MovieDetailsResponse>()
                uiState.value = response.data?.tvShow?.toMovie()?.let { movie ->
                        UiState.Success(movie)
                    } ?: UiState.Error(UiText.PlainString("Details not found"))
            } else {
                uiState.value = UiState.Error(networkResponse.message)
            }
        }
    }

}

sealed class MovieDetailsScreenIntent {
    data class GetDetails(val movieId: Int) : MovieDetailsScreenIntent()
    data class Refresh(val movieId: Int) : MovieDetailsScreenIntent()
}