package com.bingebuddy.app.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Movie
import androidx.compose.material.icons.sharp.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import com.bingebuddy.app.navigation.BingeBuddyRoutes

sealed class BottomNavigationItem(val route: String, val label: String, val icon: ImageVector) {
    data object Movies : BottomNavigationItem(HomeRoutes.MOVIES, "Movies", icon = Icons.Sharp.Movie)
    data object TvSeries : BottomNavigationItem(HomeRoutes.TV_SERIES, "Tv Series", icon = Icons.Sharp.Tv)
}