package com.bingebuddy.app.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.model.DiscoverMovieResultModel


@Composable
fun DiscoverMovieListCard(movie: DiscoverMovieResultModel, modifier: Modifier = Modifier, onClick: (movieId: String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
            .clickable {
                onClick("${movie.id}")
            }
    ) {
        Box {
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                ImageWithShimmer(
                    imageUrl = "${StringConstants.IMAGE_BASE_URL}${movie.posterPath}",
                    modifier = Modifier
                        .weight(5f)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    errorBuilder = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.BrokenImage,
                                contentDescription = null,
                                Modifier.size(50.dp)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "${movie.title}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Release - ${movie.releaseDate}",
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(modifier = Modifier.height(5.dp))
                RatingChip(movie.voteAverage, movie.voteCount)
            }
            if (movie.adult == true) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                        .align(Alignment.TopStart)// Offset for better positioning
                ) {
                    Text(
                        text = "Adult(18+)",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center).padding(5.dp)
                    )
                }
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