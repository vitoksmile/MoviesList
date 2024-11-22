@file:OptIn(FormatStringsInDatetimeFormats::class)

package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.data.BuildConfig
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.ProductionCompany
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

internal fun List<MovieModel>.toMovies(genres: Map<Int, String>): List<Movie> {
    return map { it.toMovie(genres) }
}

private fun MovieModel.toMovie(genres: Map<Int, String>): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath.buildImageUrl(),
        genres = genreIds.mapNotNull { genreId -> genres[genreId] },
        vote = vote,
    )
}

internal fun MovieDetailsModel.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath.buildImageUrl(),
        genres = genres.map { it.name },
        releaseDate = LocalDate.parse(
            input = releaseDate,
            format = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        ),
        vote = vote,
        productionCompanies = productionCompanies.toProductionCompanies(),
    )
}

private fun List<ProductionCompanyModel>.toProductionCompanies(): List<ProductionCompany> {
    return mapNotNull {
        ProductionCompany(
            name = it.name,
            logoUrl = it.logoPath?.buildImageUrl() ?: return@mapNotNull null,
        )
    }
}

private fun String.buildImageUrl(): String {
    return "${BuildConfig.IMAGE_BASE_URL}$this"
}
