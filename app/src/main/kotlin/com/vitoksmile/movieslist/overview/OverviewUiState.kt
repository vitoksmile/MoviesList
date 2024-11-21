package com.vitoksmile.movieslist.overview

import androidx.compose.runtime.Immutable
import com.vitoksmile.movieslist.domain.models.Movie

@Immutable
sealed interface OverviewUiState {

    data object Loading : OverviewUiState

    data object Error : OverviewUiState

    @Immutable
    data class Success(val movies: List<Movie>) : OverviewUiState
}
