package com.ott.ui.movie.details

import androidx.lifecycle.viewModelScope
import com.ott.data.Resource
import com.ott.data.movie.data.toMovie
import com.ott.data.movie.usecase.GetMovieDetailsUseCase
import com.ott.common.BaseViewModel
import com.ott.core_ui.util.UiText
import com.ott.data.movie.data.domain.model.Movie
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<UiState<Movie>>(UiState.None) {

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite = _isFavourite.asStateFlow()

    init {
        onAction(MovieDetailsIntent.GetDetails)
    }

    fun onAction(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.GetDetails -> {
                getMovieDetails(movieId)
            }

            is MovieDetailsIntent.Refresh -> {
                getMovieDetails(movieId)
            }

            is MovieDetailsIntent.Favourite -> {
                _isFavourite.value = !_isFavourite.value
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            emitData(UiState.Loading)
            val response = movieDetailsUseCase(movieId)
            when (response) {
                is Resource.Error -> {
                    emitData(UiState.Error(response.message))
                }

                is Resource.Success -> {
                    handleSuccessResponse(response.data)
                }
            }
        }
    }

    private fun handleSuccessResponse(response: MovieDetailsResponse?) {
        val state = response?.tvShow?.toMovie()?.let { movie ->
            UiState.Success(movie)
        } ?: UiState.Error(UiText.PlainString("Details not found"))
        emitData(state)
    }

}

sealed class MovieDetailsIntent {
    object GetDetails : MovieDetailsIntent()
    object Refresh : MovieDetailsIntent()
    object Favourite : MovieDetailsIntent()
}