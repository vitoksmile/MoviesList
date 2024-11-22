package com.vitoksmile.movieslist.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.vitoksmile.movieslist.domain.models.MovieDetails
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.random.Random

val previewEvents: DetailsUiEvents
    @Composable
    get() = remember {
        object : DetailsUiEvents {
            override fun navigateBack() {}
        }
    }

val previewDetails: MovieDetails
    @Composable
    get() = remember {
        val id = Random.nextInt()
        MovieDetails(
            id = id,
            title = "Title $id",
            overview = LoremIpsum(10).values.joinToString(separator = " "),
            posterUrl = "",
            genres = listOf("Science Fiction", "Action", "Adventure").shuffled(),
            releaseDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            vote = Random.nextDouble(0.0, 10.0),
            productionCompanies = emptyList(),
        )
    }