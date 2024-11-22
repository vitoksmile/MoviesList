@file:Suppress("ConvertArgumentToSet")

package com.vitoksmile.movieslist.data.source.remote

import app.cash.turbine.test
import com.vitoksmile.movieslist.data.source.MoviesDataSource
import com.vitoksmile.movieslist.domain.models.MovieId
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class RemoteDataSourceTest {

    private val api: MoviesApi = mockk()
    private val dataSource: MoviesDataSource = RemoteDataSource(api)

    @Test
    fun `when genres API failed, then ignore genres`() = runTest {
        coEvery { api.getGenres() } throws RuntimeException()
        val movies = MoviesListModel(movies = List(10) { mockMovieModel() })
        coEvery { api.getAll() } returns movies

        dataSource.getAll().test {
            assertEquals(
                awaitItem(),
                movies.movies.toMovies(genres = emptyMap()),
            )
            awaitComplete()
        }

        coVerify(exactly = 1) { api.getGenres() }
        coVerify(exactly = 1) { api.getAll() }
    }

    @Test
    fun `when genres API miss some values, then ignore those genres`() = runTest {
        val genres = List(5) { mockGenreMovie(id = it) }
        coEvery { api.getGenres() } returns GenresListModel(genres)
        val genreIds = genres.map { it.id + genres.size / 2 }
        val movies = MoviesListModel(movies = List(10) { mockMovieModel(genreIds) })
        coEvery { api.getAll() } returns movies

        dataSource.getAll().test {
            val item = awaitItem()
            assertEquals(
                item,
                movies.movies.toMovies(genres.associate { it.id to it.name }),
            )

            val expectedGenresCount = genres.map { it.id }.intersect(genreIds).size
            item.forEach { assertEquals(expectedGenresCount, it.genres.size) }

            awaitComplete()
        }

        coVerify(exactly = 1) { api.getGenres() }
        coVerify(exactly = 1) { api.getAll() }
    }

    @Test
    fun `when getAll API failed, then return error`() = runTest {
        val genres = List(5) { mockGenreMovie(id = it) }
        coEvery { api.getGenres() } returns GenresListModel(genres)
        val error = RuntimeException()
        coEvery { api.getAll() } throws error

        dataSource.getAll().test {
            assertEquals(error, awaitError())
        }

        coVerify(exactly = 1) { api.getGenres() }
        coVerify(exactly = 1) { api.getAll() }
    }

    @Test
    fun `when getDetails API failed, then return error`() = runTest {
        val id: MovieId = -1
        val error = RuntimeException()
        coEvery { api.getDetails(id) } throws error

        dataSource.getDetails(id).test {
            assertEquals(error, awaitError())
        }

        coVerify(exactly = 0) { api.getGenres() }
        coVerify(exactly = 1) { api.getDetails(id) }
    }

    @Test
    fun `when getDetails API successful, then return details`() = runTest {
        val id: MovieId = -1
        val details: MovieDetailsModel = mockMovieDetails()
        coEvery { api.getDetails(id) } returns details

        dataSource.getDetails(id).test {
            assertEquals(details.toMovieDetails(), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 0) { api.getGenres() }
        coVerify(exactly = 1) { api.getDetails(id) }
    }

    @Test
    fun `when releaseDate has wrong format, then return error`() = runTest {
        val id: MovieId = -1
        val details: MovieDetailsModel = mockMovieDetails(releaseDate = "2024/11/22")
        coEvery { api.getDetails(id) } returns details

        dataSource.getDetails(id).test {
            val error = awaitError()
            assertTrue(
                "mapper must throw DateTimeFormatException, but was $error",
                awaitError() is IllegalArgumentException,
            )
        }

        coVerify(exactly = 0) { api.getGenres() }
        coVerify(exactly = 1) { api.getDetails(id) }
    }

    private fun mockMovieModel(
        genreIds: List<Int> = List(10) { Random.nextInt() },
    ): MovieModel {
        val id = Random.nextInt()
        return MovieModel(
            id = id,
            title = "Title $id",
            posterPath = id.toString(),
            genreIds = genreIds,
            vote = Random.nextDouble(),
        )
    }

    private fun mockGenreMovie(id: Int = Random.nextInt()): GenreModel {
        return GenreModel(
            id = id,
            name = "Name $id",
        )
    }

    private fun mockMovieDetails(releaseDate: String = "2024-11-22"): MovieDetailsModel {
        val id = Random.nextInt()
        return MovieDetailsModel(
            id = id,
            title = "Title $id",
            overview = "Overview $id",
            posterPath = id.toString(),
            genres = emptyList(),
            releaseDate = releaseDate,
            vote = Random.nextDouble(),
            productionCompanies = emptyList(),
        )
    }
}
