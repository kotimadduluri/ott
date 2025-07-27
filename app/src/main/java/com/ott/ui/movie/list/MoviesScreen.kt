package com.moviemax.view.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ott.model.movie.data.domain.model.Movie
import com.ott.core_ui.component.list.VerticalList
import com.ott.core_ui.component.state.ActionState
import com.ott.core_ui.component.state.ActionStateView
import com.ott.core_ui.component.AppContainer
import com.ott.core_ui.component.state.ActionStateViewCard
import com.ott.core_ui.util.UiText
import com.ott.ui.UiState
import com.ott.ui.viewmodel.movie.MoviesScreenIntent
import com.ott.ui.movie.list.MovieCard
import com.ott.ui.viewmodel.movie.MoviesScreenViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesScreenViewModel = koinViewModel(),
    showDetails : (id:Int) -> Unit ={}
) {

    val uiState: State<UiState> = viewModel.uiState().collectAsStateWithLifecycle()

    AppContainer(
        title = UiText.PlainString("Movies"),
        navigation = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (uiState.value) {
                is UiState.Loading -> {
                    ActionStateViewCard(
                        action = ActionState.LOADING()
                    )
                }

                is UiState.Success<*> -> {
                    val state = uiState.value.asSuccess<List<Movie>>()
                    MoviesList(state.data) { movieId ->
                        showDetails(movieId)
                    }
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(
                            message = uiState.value.asError().message
                        ),
                        isActionRequired = true
                    ) {
                        viewModel.onAction(MoviesScreenIntent.Refresh)
                    }
                }

                else -> {
                    //todo for future development purpose
                }
            }
        }
    }
}


@Composable
fun MoviesList(
    data: List<Movie>,
    modifier: Modifier = Modifier,
    onMovieItemClick: (id: Int) -> Unit
) {

    val dataElements = remember {
        data
    }

    VerticalList(
        data = dataElements,
        modifier = modifier.testTag("MoviesList")
    ) { _, movie ->
        MovieCard(movie = movie) {
            onMovieItemClick(movie.id)
        }
    }
}