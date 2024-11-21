package com.vitoksmile.movieslist.domain.usecase

import com.vitoksmile.movieslist.domain.MoviesRepository
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MoviesRepository,
) {

    operator fun invoke(id: MovieId): Flow<Result<MovieDetails>> =
        repository.getMovieDetails(id)
}
