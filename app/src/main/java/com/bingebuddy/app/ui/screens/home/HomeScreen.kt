@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bingebuddy.app.ui.screens.discovermovies.DiscoverMoviesScreen
import com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies.NowPlayingMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.popularmovies.PopularMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.topratedmovies.TopRatedMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.upcomingmovies.UpcomingMoviesSection
import com.bingebuddy.app.ui.screens.discovertvseries.DiscoverTvSeriesScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val homeNavController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                navController = homeNavController,
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Bingebuddy", style = MaterialTheme.typography.displaySmall)
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            navController = homeNavController,
            modifier = Modifier.padding(innerPadding),
        )

    }
}

@Composable
fun HomeScreenContent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "movies"
    ) {
        composable("movies") { DiscoverMoviesScreen(modifier) }
        composable("tv-series") { DiscoverTvSeriesScreen(modifier) }
    }
}


@Composable
fun BottomNavBar(navController: NavHostController, modifier: Modifier = Modifier) {


    val items = listOf(
        BottomNavigationItem.Movies,
        BottomNavigationItem.TvSeries
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    NavigationBar(modifier = modifier) {
        for (navItem in items)
            NavigationBarItem(
                selected = currentDestination?.route == navItem.route,
                onClick = {
                    if (currentDestination?.route != navItem.route) {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = {
                    Text(
                        navItem.label,
                    )
                },
                icon =
                {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                    )
                }
            )
    }
}


