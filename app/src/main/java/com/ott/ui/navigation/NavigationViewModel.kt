package com.ott.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ott.ui.navigation.Screen

class NavigationViewModel : ViewModel() {
    val backStack = mutableStateListOf<Screen>(Screen.MovieList)

    fun navigateTo(screen: Screen) {
        backStack.add(screen)
    }

    fun navigateBack() {
        backStack.removeLastOrNull()
    }
}