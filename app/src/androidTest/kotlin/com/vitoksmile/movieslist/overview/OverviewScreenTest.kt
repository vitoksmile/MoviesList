package com.vitoksmile.movieslist.overview

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.vitoksmile.movieslist.BaseTest
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.components.TEST_TAG_ERROR_BODY
import com.vitoksmile.movieslist.components.TEST_TAG_ERROR_TITLE
import com.vitoksmile.movieslist.components.TEST_TAG_LOADING_VIEW
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.overview.components.TEST_TAG_MOVIES_VIEW
import com.vitoksmile.movieslist.overview.components.TEST_TAG_MOVIE_CARD
import org.junit.Test
import kotlin.random.Random

class OverviewScreenTest : BaseTest() {

    @Test
    fun loadingState() = runUiTest(
        content = {
            OverviewScreen(
                events = object : OverviewUiEvents {
                    override fun onMovieClicked(movie: Movie) {}
                },
                state = OverviewUiState.Loading,
            )
        },
        testBody = {
            onNodeWithText(stringResource(R.string.app_name))
                .assertIsDisplayed()

            onNodeWithTag(TEST_TAG_LOADING_VIEW)
                .assertIsDisplayed()
        }
    )

    @Test
    fun errorState() {
        val error = "Unknown error"
        runUiTest(
            content = {
                OverviewScreen(
                    events = object : OverviewUiEvents {
                        override fun onMovieClicked(movie: Movie) {}
                    },
                    state = OverviewUiState.Error(error),
                )
            },
            testBody = {
                onNodeWithText(stringResource(R.string.app_name))
                    .assertIsDisplayed()

                onNodeWithTag(TEST_TAG_ERROR_TITLE)
                    .assertTextEquals(stringResource(R.string.failed_to_load))
                onNodeWithTag(TEST_TAG_ERROR_BODY)
                    .assertTextEquals(error)
            }
        )
    }

    @Test
    fun moviesListState() {
        val movies = List(10) { index ->
            Movie(
                id = index,
                title = "Title $index",
                posterUrl = "https://poster.url/$index",
                genres = listOf("Science Fiction", "Action", "Adventure").shuffled(),
                vote = Random.nextDouble(0.0, 10.0),
            )
        }

        runUiTest(
            content = {
                OverviewScreen(
                    events = object : OverviewUiEvents {
                        override fun onMovieClicked(movie: Movie) {}
                    },
                    state = OverviewUiState.Success(movies),
                )
            },
            testBody = {
                onNodeWithText(stringResource(R.string.app_name))
                    .assertIsDisplayed()

                movies.forEach { movie ->
                    onNodeWithTag(TEST_TAG_MOVIES_VIEW)
                        .performScrollToNode(hasTestTag("$TEST_TAG_MOVIE_CARD/${movie.id}"))
                        .assert(hasAnyChild(hasText(movie.title)))
                        .assert(hasAnyChild(hasText(movie.genres.joinToString())))
                        .assert(hasAnyChild(hasText(stringResource(R.string.vote, movie.vote))))
                }
            }
        )
    }
}