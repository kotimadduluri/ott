package com.ott.core_ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.ott.core_ui.component.text.Body
import com.ott.core_ui.component.text.Title
import com.ott.core_ui.theme.LocalDimens
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText

@Composable
fun MovieCard(
    movieId: String,
    posterUrl: String,
    movieTitle: UiText,
    movieSource: UiText,
    movieStartDate: UiText,
    movieStatus: UiText,
    isFavorite: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val dimens = LocalDimens.current
    var isFavorite by remember { mutableStateOf(isFavorite) }
    val context = LocalContext.current
    val thumbnailWidth = 90.dp
    ConstraintLayout(
        modifier = Modifier
            .testTag(movieId)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {

        val (card, image) = createRefs()

        CardView(
            modifier = modifier
                .constrainAs(card) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(image.bottom)
                    height = Dimension.value(95.dp)
                    width = Dimension.fillToConstraints
                }
                .clickable { onClick() },
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = thumbnailWidth),
            ) {
                Title(
                    text = movieTitle,
                    maxLines = 2,
                )
                Body(
                    text = movieSource,
                    maxLines = 1,
                )
                Body(
                    text = movieStartDate,
                    maxLines = 1,
                )

                Body(
                    text = movieStatus,
                    maxLines = 1,
                )

            }
        }

        Box(
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.value(90.dp)
                    height = Dimension.value(115.dp)
                }
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = dimens.elevMd,
                        shape = OttTheme.Shapes.small,
                        clip = false // Important: prevents clipping of shadow
                    )
                    .clip(OttTheme.Shapes.small), // Clip image corners
                contentAlignment = Alignment.TopEnd
            ) {

                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(posterUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "$movieTitle Poster",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        // Optional: You can show a progress indicator here
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        // Show a placeholder color background on error
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colorScheme.errorContainer)
                        )
                    }
                )

                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(dimens.iconLg)
                        .clickable {
                            isFavorite = !isFavorite
                        }
                        .padding(OttTheme.Dimens.xs)
                )
            }
        }
    }
}

@Preview()
@Composable
fun MovieCardPreview() {
    OttTheme {
        MovieCard(
            movieId = "123",
            movieTitle = UiText.PlainString("Inception"),
            movieSource = UiText.PlainString("Sci-Fi, Thriller"),
            movieStartDate = UiText.PlainString("Started on: 16 July 2010"),
            movieStatus = UiText.PlainString("Status: Ended"),
            posterUrl = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
            isFavorite = true,
            onClick = {}
        )
    }
}
