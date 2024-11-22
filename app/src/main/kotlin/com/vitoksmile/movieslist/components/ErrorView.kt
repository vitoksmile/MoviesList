package com.vitoksmile.movieslist.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@VisibleForTesting
const val TEST_TAG_ERROR_TITLE = "ErrorViewTitle"

@VisibleForTesting
const val TEST_TAG_ERROR_BODY = "ErrorViewBody"

@Composable
fun ErrorView(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        Text(
            modifier = Modifier.testTag(TEST_TAG_ERROR_TITLE),
            text = stringResource(R.string.failed_to_load),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.testTag(TEST_TAG_ERROR_BODY),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview
private fun ErrorViewPreview() {
    MoviesListTheme {
        ErrorView(
            message = "Unknown error",
        )
    }
}
