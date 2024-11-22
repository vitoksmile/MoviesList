package com.vitoksmile.movieslist.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme
import java.util.Locale

@Composable
fun VoteView(
    modifier: Modifier = Modifier,
    vote: Double,
) {
    val formattedVote: String = remember(vote) {
        String.format(Locale.getDefault(), "%.1f", vote)
    }

    Text(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(4.dp))
            .padding(4.dp),
        text = formattedVote,
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
