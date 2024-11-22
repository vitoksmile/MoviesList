package com.vitoksmile.movieslist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Navigates between routes.
 */
interface NavigationManager {

    fun <T : Any> navigate(route: T)

    fun navigateBack()
}

/**
 * Builds [NavHostController].
 */
interface NavigationOwner : NavigationManager {

    @Composable
    fun navController(): NavHostController
}

private class NavigationOwnerImpl : NavigationOwner {

    private var navController: NavHostController? = null

    @Composable
    override fun navController(): NavHostController {
        // Binds navController to Composable
        DisposableEffect(Unit) {
            onDispose { navController = null }
        }
        return rememberNavController()
            .also { navController = it }
    }

    override fun <T : Any> navigate(route: T) {
        navController?.navigate(route)
    }

    override fun navigateBack() {
        navController?.navigateUp()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    @Singleton
    fun providesNavigationOwner(): NavigationOwner =
        NavigationOwnerImpl()

    @Provides
    @Singleton
    fun providesNavigationManager(navigationOwner: NavigationOwner): NavigationManager =
        navigationOwner
}
