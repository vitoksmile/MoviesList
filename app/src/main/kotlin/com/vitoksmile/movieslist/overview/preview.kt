package com.vitoksmile.movieslist.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.vitoksmile.movieslist.domain.models.Movie
import kotlin.random.Random

val previewEvents: OverviewUiEvents
    @Composable
    get() = remember {
        object : OverviewUiEvents {
            override fun reloadData() {}
            override fun onMovieClicked(movie: Movie) {}
        }
    }

val previewMovies: List<Movie>
    @Composable
    get() = remember {
        List(10) { index ->
            Movie(
                id = index,
                title = "Title $index",
                posterUrl = "",
                genres = listOf("Science Fiction", "Action", "Adventure").shuffled(),
                vote = Random.nextDouble(0.0, 10.0)
            )
        }
    }

val previewMovie: Movie
    @Composable
    get() {
        val movies = previewMovies
        return remember { movies.random() }
    }
