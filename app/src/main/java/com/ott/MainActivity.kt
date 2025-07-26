package com.ott

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.moviemax.view.movie.list.MoviesScreen
import com.ott.core_ui.component.AppContainer
import com.ott.core_ui.component.card.MovieCard
import com.ott.core_ui.theme.OttTheme
import com.ott.ui.Screen
import com.ott.ui.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Map.entry

class MainActivity : ComponentActivity() {

    val viewModel : NavigationViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OttTheme{
                NavDisplay(
                    backStack = viewModel.backStack, // Your custom-managed back stack
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = { // Define custom transitions for screen changes
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },
                    entryDecorators = listOf(
                        // Add the default decorators for managing scenes and saving state
                        rememberSceneSetupNavEntryDecorator(),
                        rememberSavedStateNavEntryDecorator(),
                    ),
                    entryProvider = entryProvider { // Define your screen entries here
                        entry<Screen.MovieList> { // Entry for the Home screen
                            MoviesScreen{ }
                        }
                        entry<Screen.MovieDetails> { // Entry for the Topics screen

                        }
                    }
                )
            }
        }
    }
}