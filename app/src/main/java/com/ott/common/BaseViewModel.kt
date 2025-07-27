package com.ott.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<T>(state: T) : ViewModel() {
    private val _uiState = MutableStateFlow(state)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    val emitData: (T) -> Unit = { newState ->
        _uiState.value = newState
    }
}