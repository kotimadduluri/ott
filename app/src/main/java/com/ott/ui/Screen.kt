package com.ott.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey{
    @Serializable
    data object MovieList : Screen
    @Serializable
    data object MovieDetails : Screen{
        fun createRoute(movieId:Int) = "MovieDetails/$movieId"
    }
}
