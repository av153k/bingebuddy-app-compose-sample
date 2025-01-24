package com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries


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
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.ui.components.DiscoverTvSeriesListCard
import com.bingebuddy.app.ui.components.DiscoverTvSeriesListShimmerCard
import com.bingebuddy.app.ui.theme.Dimension

@Composable
fun AiringTodayTvSeriesSection(
    modifier: Modifier = Modifier,
    viewmodel: AiringTodayTvSeriesViewmodel = hiltViewModel(),
) {
    Box(modifier = modifier.height(Dimension.homeSectionHeight).fillMaxWidth()) {
        when (val uiState = viewmodel.uiState) {
            is AiringTodayTvSeriesUiState.Success -> ResultView(
                movies = uiState.tvSeries
            )

            is AiringTodayTvSeriesUiState.Error -> RetryView(
                onRetry = viewmodel::getAiringTodayTvSeries
            )
            is AiringTodayTvSeriesUiState.Loading -> LoadingView()
        }
    }

}

@Composable
private fun ResultView(movies: List<DiscoverTvSeriesResultModel>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Airing Today", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(movies) {
                DiscoverTvSeriesListCard(it)
            }
        }
    }
}

@Composable
private fun RetryView(
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Airing Today", style = MaterialTheme.typography.headlineSmall)
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
private fun LoadingView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Airing Today", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(10) {
                DiscoverTvSeriesListShimmerCard()
            }
        }
    }
}