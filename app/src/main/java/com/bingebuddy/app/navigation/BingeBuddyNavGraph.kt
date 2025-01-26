package com.bingebuddy.app.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bingebuddy.app.ui.screens.home.BingeBuddyDrawer
import com.bingebuddy.app.ui.screens.home.HomeScreen
import com.bingebuddy.app.ui.screens.moviedetails.MovieDetailScreen
import com.bingebuddy.app.ui.screens.tvseriesdetails.TvSeriesDetailScreen
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@Composable
fun BingeBuddyNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    startDestination: String = BingeBuddyScreens.Home.route,
    navActions: BingeBuddyNavigationActions = remember(navHostController) {
        BingeBuddyNavigationActions(navHostController)
    }
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            BingeBuddyRoutes.HOME_ROUTE
        ) { entry ->
            BingeBuddyDrawer(
                drawerState = drawerState
            ) {
                HomeScreen(
                    navigateTo = {
                        navHostController.navigate(it)
                    }
                )
            }
        }

        composable(BingeBuddyRoutes.MOVIE_DETAILS_ROUTE) { entry ->
            MovieDetailScreen(
                navigateTo = { it ->
                    Timber.d(it)
                },
                navigateUp = { navHostController.navigateUp() }
            )

        }

        composable(BingeBuddyRoutes.TV_SERIES_DETAIL_ROUTE) { entry ->
            TvSeriesDetailScreen(navigateTo = { it ->
                Timber.d(it)
            },
                navigateUp = { navHostController.navigateUp() })
        }
    }

}