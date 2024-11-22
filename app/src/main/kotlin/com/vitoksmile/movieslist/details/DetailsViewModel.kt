@file:OptIn(ExperimentalCoroutinesApi::class)

package com.vitoksmile.movieslist.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vitoksmile.movieslist.domain.models.MovieId
import com.vitoksmile.movieslist.domain.usecase.GetMovieDetailsUseCase
import com.vitoksmile.movieslist.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DetailsViewModel @Inject constructor(
    getDetails: GetMovieDetailsUseCase,
    private val navigationManager: NavigationManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), DetailsUiEvents {

    private val movieId: MovieId = savedStateHandle.toRoute<DetailsScreenDestination>().movieId

    val uiState: StateFlow<DetailsUiState> =
        getDetails(movieId)
            .mapLatest { result ->
                result.fold(
                    onSuccess = { DetailsUiState.Success(it) },
                    onFailure = { DetailsUiState.Error(it.toString()) }
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5.seconds),
                DetailsUiState.Loading,
            )

    override fun navigateBack() {
        navigationManager.navigateBack()
    }
}

@Immutable
interface DetailsUiEvents {

    fun navigateBack()
}
