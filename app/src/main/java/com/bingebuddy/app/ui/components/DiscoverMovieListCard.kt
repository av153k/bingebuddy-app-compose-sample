package com.bingebuddy.app.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.model.DiscoverMovieResultModel


@Composable
fun DiscoverMovieListCard(movie: DiscoverMovieResultModel, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            ImageWithShimmer(
                imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "${movie.title}",
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Release - ${movie.releaseDate}",
                style = MaterialTheme.typography.labelMedium,
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
                Text(
                    "${DecimalFormat("#.0").format(movie.voteAverage)} (${movie.voteCount})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun DiscoverMovieListShimmerCard() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
            .fillMaxHeight()
            .shimmerLoading(),

        ) {}
}