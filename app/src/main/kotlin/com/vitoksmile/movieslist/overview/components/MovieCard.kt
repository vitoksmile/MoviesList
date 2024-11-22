package com.vitoksmile.movieslist.overview.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.components.VoteView
import com.vitoksmile.movieslist.domain.models.Movie
import com.vitoksmile.movieslist.overview.previewMovie
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@VisibleForTesting
const val TEST_TAG_MOVIE_CARD = "MovieCard"

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit,
) {
    val genres = remember(movie.genres) { movie.genres.joinToString() }
    val contentDescription =
        stringResource(R.string.accessibility_movie_card, movie.title, movie.vote, genres)
    Card(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
            }
            .testTag("$TEST_TAG_MOVIE_CARD/${movie.id}"),
        onClick = { onClick(movie) },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = movie.posterUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black,
                            )
                        )
                    )
            )

            VoteView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                vote = movie.vote,
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )

                Text(
                    text = genres,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
@Preview
private fun MovieCardPreview() {
    MoviesListTheme {
        MovieCard(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(128.dp),
            movie = previewMovie,
            onClick = {},
        )
    }
}
