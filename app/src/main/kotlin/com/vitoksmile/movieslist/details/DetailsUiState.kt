package com.vitoksmile.movieslist.details

import androidx.compose.runtime.Immutable
import com.vitoksmile.movieslist.domain.models.MovieDetails

@Immutable
sealed interface DetailsUiState {

    data object Loading : DetailsUiState

    data class Error(
        val message: String,
    ) : DetailsUiState

    @Immutable
    data class Success(
        val movie: MovieDetails,
    ) : DetailsUiState
}
