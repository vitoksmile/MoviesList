package com.vitoksmile.movieslist.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.vitoksmile.movieslist.BaseTest
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.components.TEST_TAG_ERROR_BODY
import com.vitoksmile.movieslist.components.TEST_TAG_ERROR_TITLE
import com.vitoksmile.movieslist.components.TEST_TAG_LOADING_VIEW
import com.vitoksmile.movieslist.components.TEST_TAG_VOTE_VIEW
import com.vitoksmile.movieslist.domain.models.MovieDetails
import com.vitoksmile.movieslist.domain.models.MovieId
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.todayIn
import org.junit.Test
import kotlin.random.Random

@OptIn(FormatStringsInDatetimeFormats::class)
class DetailsScreenTest : BaseTest() {

    @Test
    fun loadingState() = runUiTest(
        content = {
            DetailsScreen(
                events = object : DetailsUiEvents {
                    override fun navigateBack() {}
                },
                state = DetailsUiState.Loading,
            )
        },
        testBody = {
            onNodeWithTag(TEST_TAG_LOADING_VIEW)
                .assertIsDisplayed()
        }
    )

    @Test
    fun errorState() {
        val error = "Unknown error"
        runUiTest(
            content = {
                DetailsScreen(
                    events = object : DetailsUiEvents {
                        override fun navigateBack() {}
                    },
                    state = DetailsUiState.Error(error),
                )
            },
            testBody = {
                onNodeWithTag(TEST_TAG_ERROR_TITLE)
                    .assertTextEquals(stringResource(R.string.failed_to_load))
                onNodeWithTag(TEST_TAG_ERROR_BODY)
                    .assertTextEquals(error)
            }
        )
    }

    @Test
    fun detailsState() {
        val movieId: MovieId = Random.nextInt()
        val details = MovieDetails(
            id = movieId,
            title = "Title $movieId",
            overview = LoremIpsum(10).values.joinToString(),
            posterUrl = "https://poster.url/$movieId",
            genres = listOf("Science Fiction", "Action", "Adventure").shuffled(),
            releaseDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            vote = Random.nextDouble(),
            productionCompanies = emptyList(),
        )

        runUiTest(
            content = {
                DetailsScreen(
                    events = object : DetailsUiEvents {
                        override fun navigateBack() {}
                    },
                    state = DetailsUiState.Success(details),
                )
            },
            testBody = {
                onNodeWithText(details.title)
                    .assertIsDisplayed()

                onNodeWithTag(details.posterUrl)
                    .assertIsDisplayed()

                onNodeWithText(details.overview)
                    .assertIsDisplayed()

                onNodeWithText(stringResource(R.string.genres, details.genres.joinToString()))
                    .assertIsDisplayed()

                onNodeWithText(
                    stringResource(R.string.release_date, formatDate(details.releaseDate))
                )
                    .assertIsDisplayed()

                onNodeWithTag(TEST_TAG_VOTE_VIEW)
                    .assertTextEquals(stringResource(R.string.vote, details.vote))
            }
        )
    }

    private fun formatDate(date: LocalDate): String {
        val dateFormat = stringResource(R.string.date_format)
        val format = LocalDate.Format { byUnicodePattern(dateFormat) }
        return date.format(format)
    }
}