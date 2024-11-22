@file:OptIn(ExperimentalMaterial3Api::class)

package com.vitoksmile.movieslist.details

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.domain.models.MovieId
import com.vitoksmile.movieslist.components.ErrorView
import com.vitoksmile.movieslist.components.LoadingView
import com.vitoksmile.movieslist.details.components.DetailsView
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme
import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreenDestination(
    val movieId: MovieId,
)

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(events = viewModel, state = state)
}

@Composable
@VisibleForTesting
fun DetailsScreen(
    events: DetailsUiEvents,
    state: DetailsUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (state) {
                        is DetailsUiState.Loading -> {}
                        is DetailsUiState.Error -> {}

                        is DetailsUiState.Success -> {
                            Text(state.movie.title)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = events::navigateBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            modifier = Modifier.padding(paddingValues),
            targetState = state,
            label = "",
        ) {
            when (it) {
                is DetailsUiState.Loading -> {
                    LoadingView()
                }

                is DetailsUiState.Error -> {
                    ErrorView(it.message)
                }

                is DetailsUiState.Success -> {
                    DetailsView(it.movie)
                }
            }
        }
    }
}

@Composable
@Preview
private fun SuccessStatePreview() {
    MoviesListTheme {
        DetailsScreen(
            events = previewEvents,
            state = DetailsUiState.Success(previewDetails)
        )
    }
}

@Composable
@Preview
private fun LoadingStatePreview() {
    MoviesListTheme {
        DetailsScreen(
            events = previewEvents,
            state = DetailsUiState.Loading,
        )
    }
}

@Composable
@Preview
private fun ErrorStatePreview() {
    MoviesListTheme {
        DetailsScreen(
            events = previewEvents,
            state = DetailsUiState.Error("Unknown error"),
        )
    }
}
