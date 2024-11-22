package com.vitoksmile.movieslist.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme

@VisibleForTesting
const val TEST_TAG_LOADING_VIEW = "LoadingView"

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TEST_TAG_LOADING_VIEW),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
private fun LoadingViewPreview() {
    MoviesListTheme {
        LoadingView()
    }
}
