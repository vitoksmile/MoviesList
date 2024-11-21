package com.vitoksmile.movieslist.domain.models

import kotlinx.datetime.LocalDate

data class MovieDetails(
    val id: MovieId,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val genres: List<String>,
    val releaseDate: LocalDate,
    val vote: Double,
    val votesCount: Int,
    val productionCompanies: List<MovieProductionCompany>,
)
