package com.vitoksmile.movieslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vitoksmile.movieslist.navigation.NavigationOwner
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationOwner: NavigationOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesListTheme {
                AppGraph(navigationOwner)
            }
        }
    }
}
