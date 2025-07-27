package com.ott

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.ott.ui.movie.list.MoviesScreen
import com.ott.core_ui.theme.OttTheme
import com.ott.ui.navigation.Screen
import com.ott.ui.movie.details.MovieDetailsScreen
import com.ott.ui.navigation.NavigationViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    val navigationViewModel : NavigationViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OttTheme{
                NavDisplay(
                    backStack = navigationViewModel.backStack, // Your custom-managed back stack
                    entryDecorators = listOf(
                        // Add the default decorators for managing scenes and saving state
                        rememberSceneSetupNavEntryDecorator(),
                        rememberSavedStateNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = { // Define custom transitions for screen changes
                        slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                    },
                    entryProvider = entryProvider { // Define your screen entries here
                        entry<Screen.MovieList> { // Entry for the Home screen
                            MoviesScreen{movieId->
                                navigationViewModel.navigateTo(Screen.MovieDetails(movieId))
                            }
                        }
                        entry<Screen.MovieDetails> { key ->// Entry for the Topics screen
                            MovieDetailsScreen(
                                viewModel  = koinViewModel {
                                    parametersOf(key.movieId)
                                }
                            ) {
                                navigationViewModel.navigateBack()
                            }
                        }
                    },
                )
            }
        }
    }
}