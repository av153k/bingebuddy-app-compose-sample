package com.bingebuddy.app.ui.screens.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.network.model.MediaType
import com.bingebuddy.app.data.toMovie
import com.bingebuddy.app.data.toTvSeries
import com.bingebuddy.app.navigation.BingeBuddyScreens
import com.bingebuddy.app.ui.components.DiscoverMovieListCard
import com.bingebuddy.app.ui.components.DiscoverTvSeriesListCard
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    navigateTo: (route: String) -> Unit,
    navigateUp: () -> Unit,
    viewmodel: WatchlistViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewmodel.allData.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title =
                {
                    Text("Watchlist", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "back-from-watchlist"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is WatchlistUiState.Error -> RetryView(
                    errorMessage = (uiState as WatchlistUiState.Error).errorMessage,
                    onRetry = {

                    }
                )

                WatchlistUiState.Loading -> LoadingView()
                is WatchlistUiState.Success -> WatchlistItemsView(
                    items = (uiState as WatchlistUiState.Success).watchlistItems,
                    onCardClicked = navigateTo,
                )
            }
        }
    }
}


@Composable
private fun WatchlistItemsView(
    items: List<WatchlistItem>,
    onCardClicked: (route: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Nothing in watchlist yet!!", textAlign = TextAlign.Center)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            ) {
            items(items) { item ->
                Box(
                    modifier = Modifier.height(390.dp)
                ) {
                    if (item.type == "movie") {
                        Timber.d(item.toMovie().toString())
                        DiscoverMovieListCard(movie = item.toMovie(), onClick = {
                            onCardClicked(
                                BingeBuddyScreens.MovieDetails("${item.tmdbId}").route
                            )
                        }, typeBadge = {
                            Text(
                                "  Movie  ",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.background),
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = CircleShape)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color = MaterialTheme.colorScheme.onBackground)
                            )
                        }
                        )
                    } else {
                        DiscoverTvSeriesListCard(
                            tvSeries = item.toTvSeries(),
                            onTvSeriesClicked = {
                                onCardClicked(
                                    BingeBuddyScreens.TvSeriesDetails("${item.tmdbId}").route
                                )
                            },
                            typeBadge = {
                                Text(
                                    "  TV  ",
                                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.background),
                                    modifier = Modifier
                                        .shadow(elevation = 8.dp, shape = CircleShape)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(color = MaterialTheme.colorScheme.onBackground)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun RetryView(
    errorMessage: String?,
    onRetry: () -> Unit, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(errorMessage ?: "Something went wrong", style = MaterialTheme.typography.bodyMedium)
        Button(
            onClick = { onRetry() }
        ) {
            Text("Retry")
        }
    }


}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
        )
    }
}


