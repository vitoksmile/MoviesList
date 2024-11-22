package com.vitoksmile.movieslist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vitoksmile.movieslist.details.DetailsScreen
import com.vitoksmile.movieslist.details.DetailsScreenDestination
import com.vitoksmile.movieslist.details.DetailsViewModel
import com.vitoksmile.movieslist.navigation.NavigationOwner
import com.vitoksmile.movieslist.overview.OverviewScreen
import com.vitoksmile.movieslist.overview.OverviewScreenDestination
import com.vitoksmile.movieslist.overview.OverviewViewModel

@Composable
fun AppGraph(navigationOwner: NavigationOwner) {
    NavHost(
        navController = navigationOwner.navController(),
        startDestination = OverviewScreenDestination,
    ) {
        composable<OverviewScreenDestination> {
            OverviewScreen(hiltViewModel<OverviewViewModel>())
        }

        composable<DetailsScreenDestination> {
            DetailsScreen(hiltViewModel<DetailsViewModel>())
        }
    }
}
