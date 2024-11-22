@file:OptIn(ExperimentalCoroutinesApi::class)

package com.vitoksmile.movieslist.overview

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitoksmile.movieslist.navigation.NavigationManager
import com.vitoksmile.movieslist.details.DetailsScreenDestination
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
    private val navigationManager: NavigationManager,
) : ViewModel(), OverviewUiEvents {

    val uiState: StateFlow<OverviewUiState> =
        getMovies()
            .mapLatest { result ->
                result.fold(
                    onSuccess = { OverviewUiState.Success(it) },
                    onFailure = { OverviewUiState.Error(it.toString()) }
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                initialValue = OverviewUiState.Loading,
            )

    override fun onMovieClicked(movie: Movie) {
        navigationManager.navigate(DetailsScreenDestination(movie.id))
    }
}

@Immutable
interface OverviewUiEvents {

    fun onMovieClicked(movie: Movie)
}
