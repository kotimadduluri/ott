package com.ott.ui.movie.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.common.ui.components.state.ActionStateView
import com.ott.core_ui.component.AppContainer
import com.ott.core_ui.component.actionbar.DefaultNavigationIcon
import com.ott.core_ui.component.button.ButtonWithProgressBar
import com.ott.core_ui.component.slider.ImageSlider
import com.ott.core_ui.component.state.ActionState
import com.ott.core_ui.component.state.ActionStateViewCard
import com.ott.core_ui.component.text.Body
import com.ott.core_ui.component.text.Title
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText
import com.ott.model.movie.data.domain.model.Movie
import com.ott.ui.UiState
import com.ott.ui.viewmodel.movie.MovieDetailsScreenIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    uiState: State<UiState>,
    event: (intent: MovieDetailsScreenIntent) -> Unit
) {

    val isFavourite = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        event(MovieDetailsScreenIntent.GetDetails(movieId))
    }

    AppContainer(
        navigation = {
            DefaultNavigationIcon {
              //  navHostController.popBackStack()
            }
        },
        toolbarActions = {
            Icon(
                imageVector = if (isFavourite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavourite.value) "Remove from favorites" else "Add to favorites",
                tint = if (isFavourite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable { isFavourite.value = !isFavourite.value }
            )
        }
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
                    val response = (uiState.value as UiState.Success<Movie>).data
                    MovieDetailsSection(response)
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(
                            message = (uiState.value as UiState.Error).message,
                        ),
                        isActionRequired = true
                    ) {
                        event(MovieDetailsScreenIntent.Refresh(movieId))
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun MovieDetailsSection(movie: Movie) {
    val scrollState: ScrollState = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        with(movie) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(state = scrollState)
            ) {
                //image gallery
                pictures?.let {
                    ImageSlider(it)
                }

                Spacer(modifier = Modifier.size(OttTheme.Dimens.sm))

                Title(
                    text = UiText.PlainString(name),
                    textStyle = MaterialTheme.typography.titleLarge,
                    fontColor = MaterialTheme.colorScheme.onBackground
                )

                Row {
                    Body(
                        text = UiText.PlainString("Rating : $rating ($ratingCount) | "),
                        textStyle = MaterialTheme.typography.labelSmall,
                    )

                    Body(
                        text = UiText.PlainString(movie.status),
                        textStyle = MaterialTheme.typography.labelSmall,
                    )
                }

                Spacer(modifier = Modifier.size(OttTheme.Dimens.sm))

                ButtonWithProgressBar(
                    text = UiText.PlainString("Play"),
                )

                Spacer(modifier = Modifier.size(OttTheme.Dimens.xs))

                ButtonWithProgressBar(
                    text = UiText.PlainString(
                        "Download"
                    )
                )

                Spacer(modifier = Modifier.size(OttTheme.Dimens.sm))

                Body(
                    text = UiText.PlainString(description ?: "No description available"),
                )

            }
        }
    }
}