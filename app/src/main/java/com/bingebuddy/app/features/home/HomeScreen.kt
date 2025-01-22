@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.features.home

import android.graphics.Movie
import android.nfc.cardemulation.CardEmulation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bingebuddy.app.data.model.MovieModel
import com.bingebuddy.app.ui.theme.BingeBuddyTheme

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
    LazyColumn(modifier = modifier.padding(10.dp)) {
        item {
            NowPlayingSection(modifier = Modifier.height(380.dp))
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            PopularSection(modifier = Modifier.height(380.dp))
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            UpcomingSection(modifier = Modifier.height(380.dp))
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            TopRatedSection(modifier = Modifier.height(380.dp))
        }
    }
}

@Composable
fun NowPlayingSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier,) {
        Text("Now Playing", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(100) {
                MovieCard(
                    movie = MovieModel(
                        name = "Marco",
                        year = "2024",
                        rating = 3.75,
                        poster = "https://image.tmdb.org/t/p/original/il3ao5gcF6fZNqo1o9o7lusmEyU.jpg"
                    )
                )
            }
        }
    }
}

@Composable
fun PopularSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier,) {
        Text("Popular", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(100) {
                MovieCard(
                    movie = MovieModel(
                        name = "Mufasa: The Lion King",
                        year = "2024",
                        rating = 3.7,
                        poster = "https://image.tmdb.org/t/p/original/nHhjqeJcaQKOBCd21c1kV2DK5gm.jpg"
                    )

                )
            }
        }
    }
}

@Composable
fun UpcomingSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier,) {
        Text("Upcoming", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(100) {
                MovieCard(
                    movie = MovieModel(
                        name = "Captain America: Brave New World",
                        year = "2025",
                        rating = 0.0,
                        poster = "https://image.tmdb.org/t/p/original/z0ujnXounP4yq637zyLBiZThF7Y.jpg"
                    )
                )
            }
        }
    }
}

@Composable
fun TopRatedSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier,) {
        Text("Top Rated", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1)
        ) {
            items(100) {
                MovieCard(
                    movie = MovieModel(
                        name = "The Dark Knight",
                        year = "2008",
                        rating = 4.25,
                        poster = "https://image.tmdb.org/t/p/original/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
                    )
                )
            }
        }
    }
}

@Composable
fun MovieCard(movie: MovieModel, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = "movie-card", modifier = Modifier
                    .clip(shape = RoundedCornerShape(3)),
                contentScale = ContentScale.Inside,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "${movie.name}(${movie.year})",
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "rating icon",
                    tint = Color.Yellow,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("${movie.rating}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview
@Composable
fun MovieCardPreview() {
    BingeBuddyTheme {
        MovieCard(
            movie = MovieModel(
                name = "The Dark Knight",
                year = "2008",
                rating = 4.25,
                poster = "https://image.tmdb.org/t/p/original/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            )
        )
    }
}