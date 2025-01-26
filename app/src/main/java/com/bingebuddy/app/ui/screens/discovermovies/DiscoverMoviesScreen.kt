package com.bingebuddy.app.ui.screens.discovermovies

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies.NowPlayingMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.popularmovies.PopularMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.topratedmovies.TopRatedMoviesSection
import com.bingebuddy.app.ui.screens.discovermovies.upcomingmovies.UpcomingMoviesSection

@Composable
fun DiscoverMoviesScreen(onMovieClicked: (movieId: String) -> Unit,  modifier: Modifier = Modifier,) {
    LazyColumn(modifier = modifier.padding(10.dp)) {
        item {
            NowPlayingMoviesSection(onMovieClick = onMovieClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            PopularMoviesSection(onMovieClick = onMovieClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            TopRatedMoviesSection(onMovieClick = onMovieClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            UpcomingMoviesSection(onMovieClick = onMovieClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }


    }
}