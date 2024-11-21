package com.vitoksmile.movieslist.domain

import app.cash.turbine.test
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import com.vitoksmile.movieslist.domain.usecase.GetMovieDetailsUseCase
import com.vitoksmile.movieslist.domain.usecase.GetMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val useCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

    @Test
    fun `when repository returns empty flow, then useCase just completed`() = runTest {
        val id: MovieId = -1
        every { repository.getMovieDetails(any()) } returns emptyFlow()

        useCase(id).test {
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovieDetails(id) }
    }

    @Test
    fun `when repository returns error, then useCase returns error`() = runTest {
        val id: MovieId = -1
        val error = RuntimeException()
        every { repository.getMovieDetails(any()) } returns flowOf(Result.failure(error))

        useCase(id).test {
            awaitItem().assertIsFailure(error)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovieDetails(id) }
    }

    @Test
    fun `when repository returns success, then useCase returns success`() = runTest {
        val id: MovieId = -1
        val movieDetails: MovieDetails = mockk()
        every { repository.getMovieDetails(id) } returns flowOf(Result.success(movieDetails))

        useCase(id).test {
            awaitItem().assertIsSuccess(movieDetails)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovieDetails(id) }
    }

    @Test
    fun `when repository emits many times, then useCase emits many times`() = runTest {
        val id: MovieId = -1
        val movies: List<MovieDetails> = List(10) { mockk() }
        every { repository.getMovieDetails(any()) } returns flow {
            movies.forEach { emit(Result.success(it)) }
        }

        useCase(id).test {
            repeat(movies.size) {
                awaitItem().assertIsSuccess(movies[it])
            }
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovieDetails(id) }
    }
}
