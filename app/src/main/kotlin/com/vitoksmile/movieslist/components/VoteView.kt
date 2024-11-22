package com.vitoksmile.movieslist.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@VisibleForTesting
const val TEST_TAG_VOTE_VIEW = "VoteView"

@Composable
fun VoteView(
    modifier: Modifier = Modifier,
    vote: Double,
) {
    val contentDescription = stringResource(R.string.accessibility_vote, vote)
    Text(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(4.dp))
            .padding(4.dp)
            .semantics {
                this.contentDescription = contentDescription
            }
            .testTag(TEST_TAG_VOTE_VIEW),
        text = stringResource(R.string.vote, vote),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
    )
}

@Composable
@Preview
private fun VoteViewPreview() {
    MoviesListTheme {
        VoteView(
            modifier = Modifier.padding(16.dp),
            vote = 5.75,
        )
    }
}
