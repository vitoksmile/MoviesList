package com.vitoksmile.movieslist.domain.models

typealias MovieId = Int

data class Movie(
    val id: MovieId,
    val title: String,
    val posterUrl: String,
    val genres: List<String>,
)
