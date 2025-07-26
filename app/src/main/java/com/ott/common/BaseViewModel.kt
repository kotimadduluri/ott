package com.ott.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<T>(state:T) : ViewModel() {
    fun uiState() : StateFlow<T> = uiState.asStateFlow()
    val uiState = MutableStateFlow(state)
}