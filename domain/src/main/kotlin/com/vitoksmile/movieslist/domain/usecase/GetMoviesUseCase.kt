package com.vitoksmile.movieslist.domain.usecase

import com.vitoksmile.movieslist.domain.MoviesRepository
import com.vitoksmile.movieslist.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MoviesRepository,
) {

    operator fun invoke(): Flow<Result<List<Movie>>> =
        repository.getMovies()
}
