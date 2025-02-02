package com.bingebuddy.app.ui.screens.discovermovies.topratedmovies



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
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.ui.components.DiscoverMovieListCard
import com.bingebuddy.app.ui.components.DiscoverMovieListShimmerCard
import com.bingebuddy.app.ui.theme.Dimensions

@Composable
fun TopRatedMoviesSection(
    modifier: Modifier = Modifier,
    onMovieClick : (movieId: String) -> Unit,
    viewmodel: TopRatedMoviesViewmodel = hiltViewModel(),
) {
    Box(modifier = modifier.height(Dimensions.homeSectionHeight).fillMaxWidth()) {
        when (val uiState = viewmodel.uiState) {
            is TopRatedMoviesUiState.Success -> ResultView(
                movies = uiState.movies,
                onMovieClick = onMovieClick,
            )

            is TopRatedMoviesUiState.Error -> RetryView(
                onRetry = viewmodel::getTopRatedMovies
            )
            is TopRatedMoviesUiState.Loading -> LoadingView()
        }
    }

}

@Composable
fun ResultView(movies: List<DiscoverMovieResultModel>, onMovieClick : (movieId: String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Top Rated", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(movies) {
                DiscoverMovieListCard(it, onClick = onMovieClick)
            }
        }
    }
}

@Composable
fun RetryView(
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Top Rated", style = MaterialTheme.typography.headlineSmall)
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
        Text("Top Rated", style = MaterialTheme.typography.headlineSmall)
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