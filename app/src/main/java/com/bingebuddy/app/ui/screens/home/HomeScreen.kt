@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bingebuddy.app.navigation.BingeBuddyScreens
import com.bingebuddy.app.ui.screens.discovermovies.DiscoverMoviesScreen
import com.bingebuddy.app.ui.screens.discovertvseries.DiscoverTvSeriesScreen
import timber.log.Timber

object HomeRoutes {
    const val MOVIES = "movies"
    const val TV_SERIES = "tv_series"
}

@Composable
fun HomeScreen(
    navigateTo: (route: String) -> Unit,
    openDrawer: () -> Unit,
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
                },
                navigationIcon =
                {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Sharp.Menu, contentDescription = "open-drawer")
                    }
                },
                actions = {
                    IconButton(onClick = { navigateTo(BingeBuddyScreens.Search.route) }) {
                        Icon(Icons.Sharp.Search, contentDescription = "search")
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            navController = homeNavController,
            onMovieClicked = { navigateTo(BingeBuddyScreens.MovieDetails(it).route) },
            onTvSeriesClicked = { navigateTo(BingeBuddyScreens.TvSeriesDetails(it).route) },
            modifier = Modifier.padding(innerPadding),
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenContent(
    navController: NavHostController, onMovieClicked: (movieId: String) -> Unit,
    onTvSeriesClicked: (tvSeriesId: String) -> Unit, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoutes.MOVIES
    ) {
        composable(HomeRoutes.MOVIES) {
            DiscoverMoviesScreen(
                modifier = modifier,
                onMovieClicked = {
                    Timber.d("onMovieClicked")
                    onMovieClicked(it)
                }
            )
        }
        composable(HomeRoutes.TV_SERIES) {
            DiscoverTvSeriesScreen(
                onTvSeriesClicked = onTvSeriesClicked, modifier = modifier
            )
        }
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
                        style = MaterialTheme.typography.titleMedium
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


