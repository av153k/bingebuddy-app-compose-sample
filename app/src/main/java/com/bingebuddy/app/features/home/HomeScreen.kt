@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.features.home

import android.nfc.cardemulation.CardEmulation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
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
        MovieList(
            modifier = Modifier
                .padding(innerPadding),
        )

    }
}

@Composable
fun MovieList(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
    ) {
        items(count = 100) {
            MovieCard()
        }
    }
}

@Composable
fun MovieCard(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original/uMMIeMVk1TCG3CZilpxbzFh0JKT.jpg")
                    .crossfade(true)

                    .build(),
                contentDescription = "movie-card", modifier = Modifier.clip(
                    shape = RoundedCornerShape(10)
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "The Adjustment Bureau (2023)",
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "rating icon",
                    tint = Color.Yellow,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("5.0", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview
@Composable
fun MovieCardPreview() {
    BingeBuddyTheme {
        MovieCard()
    }
}