package com.vitoksmile.movieslist.data

import com.vitoksmile.movieslist.domain.MoviesRepository
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor() : MoviesRepository {

    override fun getMovies(): Flow<Result<List<Movie>>> {
        return flowOf(Result.success(List(10) { index ->
            Movie(
                id = index,
                title = "Title $index",
                posterUrl = "https://picsum.photos/id/$index/200/300",
                genres = listOf("Science Fiction", "Action", "Adventure").shuffled(),
            )
        }))
    }

    override fun getMovieDetails(id: MovieId): Flow<Result<MovieDetails>> {
        return flowOf(Result.failure(NotImplementedError()))
    }
}
