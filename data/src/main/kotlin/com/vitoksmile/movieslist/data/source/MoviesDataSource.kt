package com.vitoksmile.movieslist.data.source

import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow

internal interface MoviesDataSource {

    fun getAll(): Flow<List<Movie>>

    fun getDetails(id: MovieId): Flow<MovieDetails>
}
