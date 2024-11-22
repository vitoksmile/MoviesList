package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.data.BuildConfig
import com.vitoksmile.movieslist.domain.models.Movie

internal fun List<MovieModel>.toMovies(genres: Map<Int, String>): List<Movie> {
    return map { it.toMovie(genres) }
}

private fun MovieModel.toMovie(genres: Map<Int, String>): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = "${BuildConfig.IMAGE_BASE_URL}$posterPath",
        genres = genreIds.mapNotNull { genreId -> genres[genreId] },
        vote = vote,
    )
}
