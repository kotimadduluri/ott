package com.ott.ui.movie.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ott.core_ui.component.AppContainer
import com.ott.core_ui.component.actionbar.DefaultNavigationIcon
import com.ott.core_ui.component.button.ButtonWithProgressBar
import com.ott.core_ui.component.slider.ImageSlider
import com.ott.core_ui.component.state.ActionState
import com.ott.core_ui.component.state.ActionStateView
import com.ott.core_ui.component.state.ActionStateViewCard
import com.ott.core_ui.component.text.Body
import com.ott.core_ui.component.text.Title
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText
import com.ott.data.movie.data.domain.model.Movie
import com.ott.ui.UiState
import com.ott.util.orDefault
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = koinViewModel(),
    exit: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value

    val isFavourite = viewModel.isFavourite.collectAsStateWithLifecycle()

    AppContainer(
        navigation = {
            DefaultNavigationIcon {
                exit()
            }
        },
        toolbarActions = {
            Icon(
                imageVector = if (isFavourite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavourite.value) "Remove from favorites" else "Add to favorites",
                tint = if (isFavourite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable {
                    viewModel.onAction(MovieDetailsIntent.Favourite)
                }
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
            when (state) {
                is UiState.Loading -> ActionStateViewCard(ActionState.LOADING())

                is UiState.Success -> MovieDetailsSection(state.data)

                is UiState.Error -> ActionStateView(
                    action = ActionState.ERROR(message = state.message),
                    isActionRequired = true
                ) {
                    viewModel.onAction(MovieDetailsIntent.Refresh)
                }

                else -> {}
            }
        }
    }
}

@Composable
fun MovieDetailsSection(movie: Movie) {
    with(movie) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(OttTheme.Dimens.sm)
        ) {
            //image gallery
            pictures?.let {
                item {
                    ImageSlider(it)
                }
            }

            item {
                Title(
                    text = UiText.PlainString(name),
                    textStyle = MaterialTheme.typography.titleLarge,
                    fontColor = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
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
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(OttTheme.Dimens.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonWithProgressBar(
                        modifier = Modifier.weight(1f),
                        text = UiText.PlainString("Play"),
                    )
                    ButtonWithProgressBar(
                        modifier = Modifier.weight(1f),
                        text = UiText.PlainString(
                            "Download"
                        )
                    )
                }
            }

            item {
                Body(text = UiText.PlainString(description.orDefault("No description available")))
            }

        }
    }
}