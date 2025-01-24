package com.bingebuddy.app.ui.screens.discovermovies.upcomingmovies

import com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies.NowPlayingMoviesUiState
import com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies.NowPlayingMoviesViewmodel


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingebuddy.app.model.DiscoverMovieResultModel
import com.bingebuddy.app.ui.components.DiscoverMovieListCard
import com.bingebuddy.app.ui.components.DiscoverMovieListShimmerCard
import com.bingebuddy.app.ui.theme.Dimension

@Composable
fun UpcomingMoviesSection(
    modifier: Modifier = Modifier,
    viewmodel: UpcomingMoviesViewmodel = hiltViewModel(),
) {
    Box(modifier = modifier.height(Dimension.homeSectionHeight).fillMaxWidth()) {
        when (val uiState = viewmodel.uiState) {
            is UpcomingMoviesUiState.Success -> ResultView(
                movies = uiState.movies
            )

            is UpcomingMoviesUiState.Error -> RetryView(
                onRetry = viewmodel::getUpcomingMovies
            )
            is UpcomingMoviesUiState.Loading -> LoadingView()
        }
    }

}

@Composable
fun ResultView(movies: List<DiscoverMovieResultModel>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Upcoming", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(movies) {
                DiscoverMovieListCard(it)
            }
        }
    }
}

@Composable
fun RetryView(
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Upcoming", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(100.dp))
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Something went wrong", style = MaterialTheme.typography.bodyMedium,)
            Button(
                onClick = onRetry
            ) {
                Text("Retry")
            }
        }
    }

}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Upcoming", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(10) {
                DiscoverMovieListShimmerCard()
            }
        }
    }
}