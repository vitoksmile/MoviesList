package com.vitoksmile.movieslist.overview.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.overview.OverviewUiEvents
import com.vitoksmile.movieslist.overview.previewEvents
import com.vitoksmile.movieslist.overview.previewMovies
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@VisibleForTesting
const val TEST_TAG_MOVIES_VIEW = "MoviesView"

@Composable
fun MoviesView(
    events: OverviewUiEvents,
    movies: List<Movie>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TEST_TAG_MOVIES_VIEW),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(movies, key = { it.id }) {
            MovieCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                movie = it,
                onClick = events::onMovieClicked,
            )
        }
    }
}

@Composable
@Preview
private fun MoviesViewPreview() {
    MoviesListTheme {
        MoviesView(
            events = previewEvents,
            movies = previewMovies,
        )
    }
}
