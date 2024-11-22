package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.data.source.MoviesDataSource
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class RemoteDataSource @Inject constructor(
    private val api: MoviesApi,
) : MoviesDataSource {

    override fun getAll(): Flow<List<Movie>> = flow {
        // Wrap with runCatching to ignore errors, genres info is not critical
        val genres = runCatching { api.getGenres().genres }
            .getOrElse { emptyList() }
            .associate { it.id to it.name }

        val movies = api.getAll().movies
            .toMovies(genres)
        emit(movies)
    }

    override fun getDetails(id: MovieId): Flow<MovieDetails> = flow {
        val details = api.getDetails(id)
            .toMovieDetails()
        emit(details)
    }
}
