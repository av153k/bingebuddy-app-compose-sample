package com.bingebuddy.app.ui.screens.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bingebuddy.app.model.DiscoverMovieResultModel
import com.bingebuddy.app.ui.components.DiscoverMovieListCard
import com.bingebuddy.app.ui.components.DiscoverMovieListShimmerCard

@Composable
fun NowPlayingSection(
    modifier: Modifier = Modifier,
    nowPlayingViewmodel: NowPlayingViewmodel = hiltViewModel(),
) {
    Box(modifier = modifier.height(380.dp).fillMaxWidth()) {
        when (val uiState = nowPlayingViewmodel.uiState) {
            is NowPlayingUiState.Success -> ResultView(
                movies = uiState.movies
            )

            is NowPlayingUiState.Error -> RetryView(
                onRetry = nowPlayingViewmodel::getNowPlayingMovies
            )
            is NowPlayingUiState.Loading -> LoadingView()
        }
    }

}

@Composable
fun ResultView(movies: List<DiscoverMovieResultModel>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Now Playing", style = MaterialTheme.typography.headlineSmall)
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
        Text("Now Playing", style = MaterialTheme.typography.headlineSmall)
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
        Text("Now Playing", style = MaterialTheme.typography.headlineSmall)
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