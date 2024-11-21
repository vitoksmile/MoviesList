@file:OptIn(ExperimentalCoroutinesApi::class)

package com.vitoksmile.movieslist.overview

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    getMovies: GetMoviesUseCase,
) : ViewModel(), OverviewUiEvents {

    val uiState: StateFlow<OverviewUiState> =
        getMovies()
            .mapLatest { result ->
                result.fold(
                    onSuccess = { OverviewUiState.Success(it) },
                    onFailure = { OverviewUiState.Error }
                )
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, initialValue = OverviewUiState.Loading)

    override fun reloadData() {}

    override fun onMovieClicked(movie: Movie) {}
}

@Immutable
interface OverviewUiEvents {

    fun reloadData()

    fun onMovieClicked(movie: Movie)
}
