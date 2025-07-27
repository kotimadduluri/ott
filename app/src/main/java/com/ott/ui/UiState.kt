package com.ott.ui

import com.ott.core_ui.util.UiText

sealed class UiState<out T> {
    object None : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: UiText) : UiState<Nothing>()
    data class MultipleError(val errors: List<UiText?>) : UiState<Nothing>()
}


// Type-safe accessors
fun <T> UiState<T>.asSuccessOrNull(): UiState.Success<T>? = this as? UiState.Success<T>
fun UiState<*>.asErrorOrNull(): UiState.Error? = this as? UiState.Error
fun UiState<*>.asMultipleErrorOrNull(): UiState.MultipleError? = this as? UiState.MultipleError