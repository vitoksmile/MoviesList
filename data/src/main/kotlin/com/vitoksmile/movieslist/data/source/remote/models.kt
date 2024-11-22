package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListModel(
    @SerialName("results")
    val movies: List<MovieModel>,
)

@Serializable
data class MovieModel(
    @SerialName("id")
    val id: MovieId,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val vote: Double,
)

@Serializable
data class MovieDetailsModel(
    @SerialName("id")
    val id: MovieId,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterUrl: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val vote: String,
    @SerialName("vote_count")
    val voteCount: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyModel>,
)

@Serializable
data class GenresListModel(
    @SerialName("genres")
    val genres: List<GenreModel>,
)

@Serializable
data class GenreModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)

@Serializable
data class ProductionCompanyModel(
    @SerialName("name")
    val title: Int,
    @SerialName("logo_path")
    val logoUrl: String?,
)
