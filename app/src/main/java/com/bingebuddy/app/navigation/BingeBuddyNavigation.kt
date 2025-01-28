package com.bingebuddy.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController

object BingeBuddyRoutes {
    const val HOME_ROUTE = "home"
    const val MOVIE_DETAILS_ROUTE = "movie/{movieId}"
    const val TV_SERIES_DETAIL_ROUTE = "tv/{tvSeriesId}"
    const val SEARCH_ROUTE = "search"
    const val SETTINGS_ROUTE = "settings"
}

sealed class BingeBuddyScreens(val route: String) {
    data object Home : BingeBuddyScreens(BingeBuddyRoutes.HOME_ROUTE)
    data class MovieDetails(val id: String) :
        BingeBuddyScreens(route = "movie/$id")

    data class TvSeriesDetails(val id: String) :
        BingeBuddyScreens(route = "tv/$id")

    data object Search: BingeBuddyScreens(BingeBuddyRoutes.SEARCH_ROUTE)

    data object Setting: BingeBuddyScreens(BingeBuddyRoutes.SETTINGS_ROUTE)
}



class BingeBuddyNavigationActions(private val navController: NavHostController) {

    fun navigateToMovieDetails(): Unit {

    }

    fun navigateToTvSeriesDetails(): Unit {

    }
}