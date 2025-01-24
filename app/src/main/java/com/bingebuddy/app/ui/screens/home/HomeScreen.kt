@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies.NowPlayingMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.popularmovies.PopularMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.topratedmovies.TopRatedMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.upcomingmovies.UpcomingMoviesSection
import com.bingebuddy.app.ui.screens.discovertvseries.DiscoverTvSeriesScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Bingebuddy", style = MaterialTheme.typography.displaySmall)
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier
                .padding(innerPadding),
        )

    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    DiscoverTvSeriesScreen(modifier = modifier)
}




