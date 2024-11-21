package com.vitoksmile.movieslist.domain

import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(): Flow<Result<List<Movie>>>

    fun getMovieDetails(id: MovieId): Flow<Result<MovieDetails>>
}
