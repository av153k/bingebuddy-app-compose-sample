package com.bingebuddy.app.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Movie
import androidx.compose.material.icons.sharp.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import com.bingebuddy.app.navigation.BingeBuddyRoutes

sealed class BottomNavigationItem(val route: String, val label: String, val icon: ImageVector) {
    data object Movies : BottomNavigationItem("movies", "Movies", icon = Icons.Sharp.Movie)
    data object TvSeries : BottomNavigationItem("tv-series", "Tv Series", icon = Icons.Sharp.Tv)
}