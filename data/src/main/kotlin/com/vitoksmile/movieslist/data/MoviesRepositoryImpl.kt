package com.vitoksmile.movieslist.data

import com.vitoksmile.movieslist.data.source.remote.RemoteDataSource
import com.vitoksmile.movieslist.domain.MoviesRepository
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : MoviesRepository {

    override fun getMovies(): Flow<Result<List<Movie>>> {
        return remoteDataSource.getAll().mapToResult()
    }

    override fun getMovieDetails(id: MovieId): Flow<Result<MovieDetails>> {
        return remoteDataSource.getDetails(id).mapToResult()
    }
}
