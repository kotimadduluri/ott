package com.ott.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ott.ui.Screen

class NavigationViewModel : ViewModel() {
    val backStack = mutableStateListOf<Screen>(Screen.MovieList)
}