@file:OptIn(FormatStringsInDatetimeFormats::class, ExperimentalComposeUiApi::class)

package com.vitoksmile.movieslist.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.components.VoteView
import com.vitoksmile.movieslist.domain.models.MovieDetails
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@Composable
fun DetailsView(details: MovieDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 4f),
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { invisibleToUser() },
                model = details.posterUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            VoteView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                vote = details.vote,
            )
        }

        Text(
            text = details.overview,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = stringResource(
                R.string.genres,
                remember(details.genres) { details.genres.joinToString() }
            ),
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = stringResource(R.string.release_date, formatDate(details.releaseDate)),
            style = MaterialTheme.typography.bodySmall,
        )

        ProductionCompaniesView(
            modifier = Modifier.fillMaxWidth(),
            companies = details.productionCompanies,
        )
    }
}

@Composable
private fun formatDate(date: LocalDate): String {
    val dateFormat = stringResource(R.string.date_format)
    val format = remember(dateFormat) { LocalDate.Format { byUnicodePattern(dateFormat) } }
    return date.format(format)
}
