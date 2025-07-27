package com.ott.support

import com.ott.data.movie.data.remote.model.Episode
import com.ott.data.movie.data.remote.model.MovieDetailsResponse
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.data.movie.data.remote.model.TvShow

object Fixtures {
    const val MOVIE_ID = 1
    const val PAGE = 1

    private val mockTvShow = TvShow(
        id = MOVIE_ID,
        name = "Test Movie",
        description = "A test movie description",
        rating = "4.5",
        ratingCount = "1234",
        status = "Released",
        pictures = listOf("image1.jpg", "image2.jpg"),
        country = "IN",
        network = "Test Network",
        startDate = "2023-01-01",
        endDate = "2023-12-31",
        imagePath = "test_image.jpg",
        imageThumbnailPath = "test_image_thumbnail.jpg",
        genres = listOf("Drama", "Action"),
        episodes = listOf(
            Episode(airDate = "2023-01-01", episode = 1, name = "Pilot", season = 1)
        )
    )


    fun getMovieDetailsResponse() = MovieDetailsResponse(tvShow = mockTvShow)

    fun getMoviesResponse() = MoviesResponse(
        page = 1,
        tvShows = listOf(
            TvShow(
                id = 1,
                name = "Test Movie",
                description = "A test movie description",
                rating = "4.5",
                ratingCount = "1234",
                status = "Released",
                pictures = listOf("image1.jpg", "image2.jpg"),
                country = "IN",
                network = "Test Network",
                startDate = "2023-01-01",
                endDate = "2023-12-31",
                imagePath = "test_image.jpg",
                imageThumbnailPath = "test_image_thumbnail.jpg",
                genres = listOf("Drama", "Action"),
                episodes = listOf(
                    com.ott.data.movie.data.remote.model.Episode(
                        airDate = "2023-01-01", episode = 1, name = "Pilot", season = 1
                    )
                )
            )
        ),
        pages = 2,
        total = "20"
    )

}