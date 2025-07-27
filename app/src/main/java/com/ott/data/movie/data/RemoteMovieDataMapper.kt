package com.ott.data.movie.data

import com.ott.data.movie.data.domain.model.MovieEpisode
import com.ott.data.movie.data.domain.model.Movie
import com.ott.data.movie.data.remote.model.Episode
import com.ott.data.movie.data.remote.model.MoviesResponse
import com.ott.data.movie.data.remote.model.TvShow

// mapper to map with ui objects from remote source
internal fun MoviesResponse.getMovies(): List<Movie> {
    return tvShows?.map { show ->
        show.toMovie()
    } ?: emptyList()
}

internal fun TvShow.toMovie(): Movie {
    return Movie(
        country = country,
        description = description,
        endDate = endDate,
        movieEpisodes = episodes?.toEpisodes(),
        genres = genres,
        id = id,
        imagePath = imagePath,
        imageThumbnailPath = imageThumbnailPath,
        name = name,
        pictures = pictures,
        rating = rating,
        ratingCount = ratingCount,
        startDate = startDate,
        status = status,
        network = network,
    )
}

internal fun List<Episode>.toEpisodes(): List<MovieEpisode> {
    return map {
        it.toEpisode()
    }
}

internal fun Episode.toEpisode(): MovieEpisode {
    return MovieEpisode(
        airDate = airDate,
        episode = episode,
        name = name,
        season = season
    )
}