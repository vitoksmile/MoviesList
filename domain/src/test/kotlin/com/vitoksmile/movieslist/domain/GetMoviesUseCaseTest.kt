package com.vitoksmile.movieslist.domain

import app.cash.turbine.test
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.usecase.GetMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val useCase: GetMoviesUseCase = GetMoviesUseCase(repository)

    @Test
    fun `when repository returns empty flow, then useCase just completed`() = runTest {
        every { repository.getMovies() } returns emptyFlow()

        useCase().test {
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository returns error, then useCase returns error`() = runTest {
        val error = RuntimeException()
        every { repository.getMovies() } returns flowOf(Result.failure(error))

        useCase().test {
            awaitItem().assertIsFailure(error)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository returns success, then useCase returns success`() = runTest {
        val movies: List<Movie> = List(10) { mockk() }
        every { repository.getMovies() } returns flowOf(Result.success(movies))

        useCase().test {
            awaitItem().assertIsSuccess(movies)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository emits many times, then useCase emits many times`() = runTest {
        val movies: List<List<Movie>> = List(10) { List(10) { mockk() } }
        every { repository.getMovies() } returns flow {
            movies.forEach { emit(Result.success(it)) }
        }

        useCase().test {
            repeat(movies.size) {
                awaitItem().assertIsSuccess(movies[it])
            }
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMovies() }
    }
}
