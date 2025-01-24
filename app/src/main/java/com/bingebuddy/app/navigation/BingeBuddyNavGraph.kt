package com.bingebuddy.app.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bingebuddy.app.ui.screens.home.BingeBuddyDrawer
import com.bingebuddy.app.ui.screens.home.HomeScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun BingeBuddyNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    startDestination: String = BingeBuddyRoutes.HOME_ROUTE,
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
                HomeScreen()
            }
        }
    }

}