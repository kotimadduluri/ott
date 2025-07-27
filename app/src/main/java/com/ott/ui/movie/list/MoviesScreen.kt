package com.ott.ui.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ott.core_ui.component.AppContainer
import com.ott.core_ui.component.list.VerticalList
import com.ott.core_ui.component.state.ActionState
import com.ott.core_ui.component.state.ActionStateView
import com.ott.core_ui.component.state.ActionStateViewCard
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText
import com.ott.data.movie.data.domain.model.Movie
import com.ott.ui.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    showDetails: (id: Int) -> Unit = {}
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value

    AppContainer(
        title = UiText.PlainString("Movies"),
        navigation = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(OttTheme.Dimens.md),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (state) {
                UiState.Loading -> ActionStateViewCard(ActionState.LOADING())

                is UiState.Error -> ActionStateView(
                    action = ActionState.ERROR(message = state.message),
                    isActionRequired = true
                ) {
                    viewModel.onAction(MoviesScreenIntent.Refresh)
                }

                is UiState.Success -> {
                    val movies = state.data
                    if (movies.isNotEmpty()) {
                        MoviesList(movies, onMovieItemClick = showDetails)
                    } else {
                        ActionStateView(
                            action = ActionState.ERROR(
                                message = UiText.PlainString("No Movies Found"),
                            ),
                            isActionRequired = true
                        ) {
                            viewModel.onAction(MoviesScreenIntent.Refresh)
                        }
                    }
                }

                else -> {}
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