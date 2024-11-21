package com.vitoksmile.movieslist.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.overview.OverviewUiEvents
import com.vitoksmile.movieslist.overview.previewEvents
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@Composable
fun ErrorView(
    events: OverviewUiEvents,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.overview_error),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
        )

        OutlinedButton(
            modifier = Modifier.padding(top = 8.dp),
            onClick = events::reloadData,
        ) {
            Text(stringResource(R.string.overview_try_again))
        }
    }
}

@Composable
@Preview
private fun ErrorViewPreview() {
    MoviesListTheme {
        ErrorView(events = previewEvents)
    }
}
