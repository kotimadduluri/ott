package com.ott.ui.movie.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ott.core_ui.component.card.MovieCard
import com.ott.core_ui.util.UiText
import com.ott.data.movie.data.domain.model.Movie
import com.ott.data.movie.getFakeMovies

@Composable
fun MovieCard(
    movie: Movie,
    onItemClicked: (item: Movie) -> Unit = {}
) {

    MovieCard(
        movieId = movie.id.toString(),
        movieTitle = UiText.PlainString(movie.name),
        movieSource = UiText.PlainString("${movie.network}[${movie.country}]"),
        movieStartDate = UiText.PlainString("Started on : ${movie.startDate}"),
        movieStatus = UiText.PlainString("Status: ${movie.status}"),
        posterUrl = movie.imageThumbnailPath,
        onClick = {
            onItemClicked(movie)
        }
    )
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = getFakeMovies()[0]
    MovieCard(movie = movie)
}