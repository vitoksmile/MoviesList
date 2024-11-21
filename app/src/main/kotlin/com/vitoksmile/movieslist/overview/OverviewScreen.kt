@file:OptIn(ExperimentalMaterial3Api::class)

package com.vitoksmile.movieslist.overview

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.overview.components.ErrorView
import com.vitoksmile.movieslist.overview.components.LoadingView
import com.vitoksmile.movieslist.overview.components.SuccessView
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@Composable
fun OverviewScreen() {
    val viewModel: OverviewViewModel = viewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    OverviewScreen(
        events = viewModel,
        state = state
    )
}

@Composable
private fun OverviewScreen(
    events: OverviewUiEvents,
    state: OverviewUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            modifier = Modifier.padding(paddingValues),
            targetState = state,
            label = "",
        ) {
            when (it) {
                is OverviewUiState.Loading -> {
                    LoadingView()
                }

                is OverviewUiState.Error -> {
                    ErrorView(events)
                }

                is OverviewUiState.Success -> {
                    SuccessView(events, it.movies)
                }
            }
        }
    }
}

@Composable
@Preview
private fun SuccessStatePreview() {
    MoviesListTheme {
        val movies = remember {
            List(10) { index ->
                Movie(
                    id = index,
                    title = "Title $index",
                    posterUrl = "",
                    genres = listOf("Science Fiction", "Action", "Adventure"),
                )
            }
        }

        OverviewScreen(
            events = previewEvents,
            state = OverviewUiState.Success(movies)
        )
    }
}

@Composable
@Preview
private fun LoadingStatePreview() {
    MoviesListTheme {
        OverviewScreen(
            events = previewEvents,
            state = OverviewUiState.Loading,
        )
    }
}

@Composable
@Preview
private fun ErrorStatePreview() {
    MoviesListTheme {
        OverviewScreen(
            events = previewEvents,
            state = OverviewUiState.Error,
        )
    }
}
